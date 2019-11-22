package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.FontGrabber;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TextboxState
        implements IState {

    public enum Mode { DEFAULT, THE_SIMPSONS; }
    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }

    private transient Handler handler;

    private Mode currentMode;
    private State currentState;

    // MESSAGE
    private String textPassedIn;
    private ArrayList<String> linesToDisplay;    //each element is a line-length-chunk of textPassedIn.
    private int indexCurrentLine;

    private int widthLetter, heightLetter; //size of each letter.
    private int xOffset, yOffset; //page margins.

    //BLINKING CURSOR
    private int speedBlink, speedTypeIn;
    private int counterBlink, counterTypeIn;
    private int indexTypeIn, indexLineOnPage;
    private boolean visible;

    // TEXT_AREA (location and size)
    int[] locationAndSize;

    // TEXT_AREA (panel and border)
    private TextArea textArea;

    // TEXT_AREA (lines)
    private ArrayList<Line> linesTemplateOfTextArea;
    private int numberOfLinesPerPage, numberOfLettersPerLine;

    public TextboxState(Handler handler) {
        this.handler = handler;

        currentMode = Mode.DEFAULT;
        currentState = State.ENTER;

        textPassedIn = null;
        linesToDisplay = new ArrayList<String>();
        indexCurrentLine = 0;

        speedBlink = 3;
        speedTypeIn = 6;
        counterBlink = 0;
        counterTypeIn = 0;
        indexTypeIn = 0;
        indexLineOnPage = 0;
        visible = false;

        locationAndSize = null;
        textArea = null;

        linesTemplateOfTextArea = new ArrayList<Line>();
        numberOfLinesPerPage = 0;
        numberOfLettersPerLine = 0;

        widthLetter = 10;
        heightLetter = 10;
        xOffset = 20;
        yOffset = 10;
    } // **** end TextboxState(Handler) constructor ****

    private void initTextLayout() {
        textArea = new TextArea(locationAndSize[0], locationAndSize[1], locationAndSize[2], locationAndSize[3]);

        //total height minus top and bottom margins (yOffset) is the amount of vertical space to work with,
        //divide the above by how much vertical space a row of letters takes up PLUS a line break (yOffset).
        numberOfLinesPerPage = (textArea.getHeightFinal() - (2 * yOffset)) / (heightLetter + yOffset);
        System.out.println("NUMBER OF LINES PER PAGE: " + numberOfLinesPerPage);
        //total width minus left and right margins (xOffset) is the amount of horizontal space to work with,
        //divide the above by how much horizontal space a column of letter/punctuation-mark takes up.
        numberOfLettersPerLine = (textArea.getWidthFinal() - (2 * xOffset)) / (widthLetter);
        System.out.println("NUMBER OF LETTERS PER LINE: " + numberOfLettersPerLine);


        //SET-UP linesToDisplay.
        StringBuilder sb = new StringBuilder();
        ///////////////////////////////////////////////
        String[] words = textPassedIn.split(" ");
        ///////////////////////////////////////////////
        int indexOfWords = 0;
        while (indexOfWords < words.length) {
            //word will fit on this line.
            if ((sb.toString().length() + words[indexOfWords].length() + 1) <= numberOfLettersPerLine) { //+1 for SPACE added after.
                sb.append(words[indexOfWords]).append(" ");
                indexOfWords++;
            }
            //store the line-worth of textPassedIn into the linesToDisplay ArrayList<String>.
            else {
                //////////////////////////////////
                linesToDisplay.add(sb.toString());
                //////////////////////////////////
                sb.delete(0, sb.length());
            }

            //store the left-over (last line).
            if (indexOfWords == words.length) {
                //////////////////////////////////
                linesToDisplay.add(sb.toString());
                //////////////////////////////////
                sb.delete(0, sb.length());
            }
        }

        //SET-UP current page.
        //instantiate the Line instances.
        int indexOfLinesTemplateOfTextArea = 0;
        while (indexOfLinesTemplateOfTextArea < numberOfLinesPerPage) {
            int y = (indexOfLinesTemplateOfTextArea + 1) * (heightLetter + yOffset);
            linesTemplateOfTextArea.add(new Line(xOffset, y));
            indexOfLinesTemplateOfTextArea++;
        }
        indexOfLinesTemplateOfTextArea = 0;

        //set the message of the Line instances.
        while (indexOfLinesTemplateOfTextArea < numberOfLinesPerPage) {
            //more linesToDisplay.
            if (indexCurrentLine < linesToDisplay.size()) {
                String messageOfLine = linesToDisplay.get(indexCurrentLine);
                linesTemplateOfTextArea.get(indexOfLinesTemplateOfTextArea).setMessage(messageOfLine);

                indexCurrentLine++;
                indexOfLinesTemplateOfTextArea++;
            }
            //blank lines (filler-lines to complete the page).
            else {
                String messageOfBlankLine = "";
                linesTemplateOfTextArea.get(indexOfLinesTemplateOfTextArea).setMessage(messageOfBlankLine);

                indexOfLinesTemplateOfTextArea++;
            }
        }
        indexOfLinesTemplateOfTextArea = 0;
    }

    private int continueIndicatorTicker = 0;
    private int continueIndicatorTickerSpeed = 30;
    private boolean renderContinueIndicator = false;
    @Override
    public void tick() {
        //getInput();

        switch (currentState) {
            case ENTER:
                //TODO: implement animationfx of textbox expanding (maybe have a bounce/over-shoot expansion size then small reduction to reach intended size).
                //textbox-background's EXPAND-IN effect.
                if (textArea.getxCurrent() > textArea.getxFinal()) {
                    textArea.setxCurrent(textArea.getxCurrent() - 5);
                } else {
                    textArea.setxCurrent(textArea.getxFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }
                if (textArea.getyCurrent() > textArea.getyFinal()) {
                    textArea.setyCurrent(textArea.getyCurrent() - 5);
                } else {
                    textArea.setyCurrent(textArea.getyFinal());
                }
                if (textArea.getWidthCurrent() < textArea.getWidthFinal()) {
                    textArea.setWidthCurrent(textArea.getWidthCurrent() + (2 * 5));
                } else {
                    textArea.setWidthCurrent(textArea.getWidthFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }
                if (textArea.getHeightCurrent() < textArea.getHeightFinal()) {
                    textArea.setHeightCurrent(textArea.getHeightCurrent() + 3);
                } else {
                    textArea.setHeightCurrent(textArea.getHeightFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State
                if ((textArea.getxCurrent() == textArea.getxFinal()) &&
                        (textArea.getyCurrent() == textArea.getyFinal()) &&
                        (textArea.getWidthCurrent() == textArea.getWidthFinal()) &&
                        (textArea.getHeightCurrent() == textArea.getHeightFinal())) {
                    changeCurrentState(State.LINE_IN_ANIMATION);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case LINE_IN_ANIMATION:
                //TODO: implement animationfx of line being "type-in".
                System.out.println("STILL NEED TO IMPLEMENT type-in effect!!!!!!!!!!!!!!!!!");

                counterBlink++;
                counterTypeIn++;

                //alternate blinking: on, off.
                if (counterBlink >= speedBlink) {
                    //reset the counter.
                    counterBlink = 0;

                    //TODO: toggle opacity from 0.0f to 1.0f.
                    visible = !visible;
                }

                //move cursor to the next character.
                if (counterTypeIn >= speedTypeIn) {
                    //reset the counter.
                    counterTypeIn = 0;

                    //TODO: move cursor to the next character.
                    //move cursor to next character, then check if should reset indexTypeIn and move to new line.
                    indexTypeIn++;

                    //see if finished with current line.
                    if (indexTypeIn >= linesTemplateOfTextArea.get(indexLineOnPage).getMessage().length()) {
                        //TODO: move to next line.

                        indexLineOnPage++;

                        if (indexLineOnPage >= (numberOfLinesPerPage-1)) {
                            indexLineOnPage = 0;

                            changeCurrentState(State.WAIT_FOR_INPUT);
                        }
                    }
                }

                /*
                //int textSpeed = 2; //actual in-game textSpeed.
                int textSpeed = 10; //developer-mode textSpeed.
                //reveal the linesToDisplay of textPassedIn by shrinking the covering-rectangle-that's-the-same-color-as-textbox-background.
                if (firstLine.getxTypeInFX() < (firstLine.getX() + (firstLine.getMessage().length() * widthLetter)) ) {
                    firstLine.setxTypeInFX( firstLine.getxTypeInFX() + textSpeed );
                    firstLine.setWidthTypeInFX( firstLine.getWidthTypeInFX() - textSpeed );
                }
                //TODO: sometimes there's only one line and we shouldn't wait for the revealing of the second line.
                //does NOT equals null.
                else if ( ( !secondLine.getMessage().equals( "" ) ) && (secondLine.getxTypeInFX() <
                        (secondLine.getX() + (secondLine.getMessage().length() * widthLetter))) ) {
                    secondLine.setxTypeInFX( secondLine.getxTypeInFX() + textSpeed );
                    secondLine.setWidthTypeInFX( secondLine.getWidthTypeInFX() - textSpeed );
                }

                // @@@@@@@@@@@@@@@@@ ACTUALLY... just set currentState to State.WAIT_FOR_INPUT @@@@@@@@@@@@@@@@
                //ending-situation where secondLine doesn't exist.
                //does equals null.
                if ( (secondLine.getMessage().equals( "" )) && (firstLine.getxTypeInFX() >=
                        (firstLine.getxTypeInFX() + (firstLine.getMessage().length() * widthLetter))) ) {
                    //else if ( (secondLine == null) && (widthTypeInFX <= 0) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }
                //secondLine exist.
                else if ( (firstLine.getxTypeInFX() >= (firstLine.getX() + (firstLine.getMessage().length() * widthLetter)))
                        && (secondLine.getxTypeInFX() >= (secondLine.getX() + (secondLine.getMessage().length() * widthLetter))) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }
                */

                break;
            case WAIT_FOR_INPUT:
                //continue-indicator should blink on-and-off if there's another page.
                if (indexCurrentLine < linesToDisplay.size()) {
                    //////////////////////////
                    continueIndicatorTicker++;
                    //////////////////////////

                    //continueIndicatorTickerSpeed will determine when to alternate the on/off effect of what's rendered.
                    if (continueIndicatorTicker >= continueIndicatorTickerSpeed) {
                        //alternate the continueIndicator's blinking effect.
                        renderContinueIndicator = !renderContinueIndicator;
                        //resets the continueIndicatorTicker when its max is reached.
                        continueIndicatorTicker = 0;
                    }
                } else {
                    renderContinueIndicator = true;
                }

                //a-button
                //if there's another page, set each line's message to the next String from linesToDisplay.
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //more linesToDisplay.
                    if (indexCurrentLine < linesToDisplay.size()) {
                        //set the message of the Line instances.
                        int indexOfLinesTemplateOfTextArea = 0;
                        while (indexOfLinesTemplateOfTextArea < numberOfLinesPerPage) {
                            if (indexCurrentLine < linesToDisplay.size()) {
                                String messageOfLine = linesToDisplay.get(indexCurrentLine);
                                linesTemplateOfTextArea.get(indexOfLinesTemplateOfTextArea).setMessage(messageOfLine);

                                indexCurrentLine++;
                                indexOfLinesTemplateOfTextArea++;
                            }
                            //blank lines (filler-lines to complete the page).
                            else {
                                String messageOfBlankLine = "";
                                linesTemplateOfTextArea.get(indexOfLinesTemplateOfTextArea).setMessage(messageOfBlankLine);

                                indexOfLinesTemplateOfTextArea++;
                            }
                        }

                        ////////////////////////////////////////////
                        changeCurrentState(State.LINE_IN_ANIMATION);
                        ////////////////////////////////////////////
                    }
                    //no more linesToDisplay.
                    else {
                        ////////////////////////////////////////////
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        ////////////////////////////////////////////
                    }
                }


                /*
                //CHECK IF THERE'S ANOTHER PAGE: so if, continue-indicator should blink on-and-off.
                if ( (currentLine1Index + 2) < linesToDisplay.size() ) {
                    //////////////////////////
                    continueIndicatorTicker++;
                    //////////////////////////

                    //continueIndicatorTickerSpeed will determine when to alternate the on/off effect of what's rendered.
                    if (continueIndicatorTicker >= continueIndicatorTickerSpeed) {
                        //alternate the continueIndicator's blinking effect.
                        renderContinueIndicator = !renderContinueIndicator;
                        //resets the continueIndicatorTicker when its max is reached.
                        continueIndicatorTicker = 0;
                    }
                }

                //a-button (if there's another page: set message/secondLine to their next String from the array of linesToDisplay).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //IF THERE'S ANOTHER PAGE: increment the currentLine#Index and re-assign message.
                    if ( (currentLine1Index + 2) < linesToDisplay.size() ) {
                        System.out.println("TextboxState.tick(), switch.WAIT_FOR_INPUT: ArrayList<String> linesToDisplay has size() == " + linesToDisplay.size());

                        currentLine1Index = currentLine1Index + 2;
                        firstLine.setMessage( linesToDisplay.get(currentLine1Index) );
                        //CHECK IF ANOTHER secondLine exist.
                        if ( (currentLine2Index + 2) < linesToDisplay.size() ) {
                            currentLine2Index = currentLine2Index + 2;
                            secondLine.setMessage( linesToDisplay.get(currentLine2Index) );
                        } else {
                            secondLine.setMessage( "" );
                        }

                        //RESET values related to textbox's type-in effect.
                        firstLine.setxTypeInFX( firstLine.getX() );
                        firstLine.setyTypeInFX( firstLine.getY() );
                        firstLine.setWidthTypeInFX( firstLine.getWidth() );
                        firstLine.setHeightTypeInFX( firstLine.getHeight() );

                        secondLine.setxTypeInFX( secondLine.getX() );
                        secondLine.setyTypeInFX( secondLine.getY() );
                        secondLine.setWidthTypeInFX( secondLine.getWidth() );
                        secondLine.setHeightTypeInFX( secondLine.getHeight() );

                        renderContinueIndicator = false;

                        ////////////////////////////////////////////
                        changeCurrentState(State.LINE_IN_ANIMATION);
                        ////////////////////////////////////////////
                    } else {
                        //if reached this line: message's currentLine1Index+2 is too big (no more linesToDisplay).
                        firstLine.setMessage( "" );
                        secondLine.setMessage( "" );

                        ////////////////////////////////////////////
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        ////////////////////////////////////////////
                    }
                }
                */

                break;
            case PAGE_OUT_ANIMATION:
                //TEXT_AREA SHRINKING EFFECT.
                if (textArea.getxCurrent() < textArea.getxInit()) {
                    textArea.setxCurrent(textArea.getxCurrent() + 5);
                } else {
                    textArea.setxCurrent(textArea.getxInit());
                }
                if (textArea.getyCurrent() < textArea.getyInit()) {
                    textArea.setyCurrent(textArea.getyCurrent() + 5);
                } else {
                    textArea.setyCurrent(textArea.getyInit());
                }
                if (textArea.getWidthCurrent() > textArea.getWidthInit()) {
                    textArea.setWidthCurrent(textArea.getWidthCurrent() - (2 * 5));
                } else {
                    textArea.setWidthCurrent(0); //when width shrink pass its INITIAL DIMENSION, set it to 0.
                }
                if (textArea.getHeightCurrent() > textArea.getHeightInit()) {
                    textArea.setHeightCurrent(textArea.getHeightCurrent() - 3);
                } else {
                    textArea.setHeightCurrent(0); //when height shrink pass its INITIAL DIMENSION, set it to 0.
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State.EXIT
                if ((textArea.getWidthCurrent() == 0) && (textArea.getHeightCurrent() == 0)) {
                    changeCurrentState(State.EXIT);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case EXIT:
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //pop.
                System.out.println("TextboxState.State.EXIT");
                handler.getStateManager().popIState();
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            default:
                System.out.println("TextboxState.tick(), switch(currentMode) construct's " +
                        "Mode.DEFAULT field's switch(currentState) construct's default.");
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        //repaint the render(Graphics) of the IState that is just below the top of the stack.
        handler.getStateManager().getStatesStack().get(handler.getStateManager().getStatesStack().size()-2).render(g);

        //TEXT_AREA - panel
        Graphics2D g2d = (Graphics2D)g;
        switch (currentMode) {
            case DEFAULT:
                Color purple = new Color(64, 0, 64);
                Color bluish = new Color(0, 128, 192);
                GradientPaint gradientPaint = new GradientPaint(0, textArea.getyFinal(), purple,
                        0, handler.getHeight()+75, bluish);
                //TODO: if using GradientPaint for text area's texture (instead of preset-solid-color), TEXT REVEAL SYSTEM must be changed.
                g2d.setPaint(gradientPaint);
                //g.setColor(Color.BLUE);
                g2d.fillRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());
                //BORDER
                g.setColor(Color.YELLOW);
                g.drawRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());

                break;
            case THE_SIMPSONS:
                float opacity = 0.75f;
                //transparent.
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.textboxStateBackground_TheSimpsons,
                        textArea.getxFinal(),
                        (int)(textArea.getyFinal() - (1.53 * textArea.getHeightFinal())),
                        textArea.getWidthFinal(),
                        (int)(textArea.getHeightFinal() + (1.58 * textArea.getHeightFinal())),
                        null);
                //back to normal.
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                break;
            default:
                System.out.println("TextboxState.render(Graphics) switch(currentMode) construct's default.");
                break;
        }

        switch (currentState) {
            case ENTER:
                //taken care of by TEXT_AREA and BORDER (outside of this switch-construct).
                //it's outside so it can be redrawn for all cases.

                break;
            case LINE_IN_ANIMATION:
                //do the following for each line on the page.
                for (int i = 0; i < numberOfLinesPerPage; i++) {
                    //current line
                    if (i == indexLineOnPage) {
                        float opacity = 1.0f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        Line currentLine = linesTemplateOfTextArea.get(indexLineOnPage);
                        String visibleMessage = currentLine.getMessage().substring(0, indexTypeIn);
                        FontGrabber.renderString(g2d, visibleMessage,
                                currentLine.getX(), currentLine.getY(),
                                widthLetter, heightLetter);

                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
                        String invisibleMessage = currentLine.getMessage().substring(indexTypeIn);
                        FontGrabber.renderString(g2d, invisibleMessage,
                                currentLine.getX() + (indexTypeIn * widthLetter), currentLine.getY(),
                                widthLetter, heightLetter);
                    }
                    //prior lines
                    else if (i < indexLineOnPage) {
                        float opacity = 1.0f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        Line priorLine = linesTemplateOfTextArea.get(i);
                        FontGrabber.renderString(g2d, priorLine.getMessage(),
                                priorLine.getX(), priorLine.getY(),
                                widthLetter, heightLetter);
                    }
                    //future lines
                    else if (i > indexLineOnPage) {
                        float opacity = 0.0f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        Line futureLine = linesTemplateOfTextArea.get(i);
                        FontGrabber.renderString(g2d, futureLine.getMessage(),
                                futureLine.getX(), futureLine.getY(),
                                widthLetter, heightLetter);
                    }
                }

                /*
                for (Line line : linesTemplateOfTextArea) {
                    //if first line being rendered... make opacity of first 3 characters 0.5f.
                    if (line.getMessage().length() >= 1) {
                        float opacity = 0.5f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        FontGrabber.renderString(g2d, line.getMessage().substring(0, 3), line.getX(), line.getY(), widthLetter, heightLetter);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                        FontGrabber.renderString(g2d, line.getMessage().substring(3), line.getX() + (3 * widthLetter), line.getY(), widthLetter, heightLetter);
                    } else {
                        FontGrabber.renderString(g2d, line.getMessage(), line.getX(), line.getY(), widthLetter, heightLetter);
                    }
                }
                */

                /*
                //TYPE-IN EFFECT (rectangles that covers message and secondLine, and reveals them by shrinking)
                //g.setColor(Color.BLUE);
                g.setColor(Color.BLACK);
                g.fillRect(firstLine.getxTypeInFX(), firstLine.getyTypeInFX(), firstLine.getWidthTypeInFX(), firstLine.getHeightTypeInFX());
                g.fillRect(secondLine.getxTypeInFX(), secondLine.getyTypeInFX(), secondLine.getWidthTypeInFX(), secondLine.getHeightTypeInFX());
                */

                break;
            case WAIT_FOR_INPUT:
                for (Line line : linesTemplateOfTextArea) {
                    //if first line being rendered... make opacity of first 3 characters 0.5f.
                    if (line.getMessage().length() >= 1) {
                        float opacity = 0.5f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        FontGrabber.renderString(g2d, line.getMessage().substring(0, 3), line.getX(), line.getY(), widthLetter, heightLetter);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                        FontGrabber.renderString(g2d, line.getMessage().substring(3), line.getX() + (3 * widthLetter), line.getY(), widthLetter, heightLetter);
                    } else {
                        FontGrabber.renderString(g2d, line.getMessage(), line.getX(), line.getY(), widthLetter, heightLetter);
                    }
                }

                /*
                //SECOND_LINE EXIST.
                //does NOT equals.
                if ( !secondLine.getMessage().equals( "" ) ) {
                    //render: secondLine
                    FontGrabber.renderString(g, secondLine.getMessage(), secondLine.getX(), secondLine.getY(), widthLetter, heightLetter);

                    // @@@@@ RENDER BLINKING continue-indicator @@@@@
                    //blinking on-state
                    if (renderContinueIndicator) {
                        g.drawImage(Assets.pokeballToken,
                                textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                                textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                                widthLetter,
                                heightLetter,
                                null);
                    }
                    //blinking off-state
                    else {
                        g.setColor(Color.BLUE);
                        g.fillRect(textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                                textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                                widthLetter,
                                heightLetter);
                    }
                }
                //SECOND_LINE DOES not EXIST.
                else if ( firstLine.getxTypeInFX() >= (firstLine.getX() + (firstLine.getMessage().length() * widthLetter)) ) {
                    //NON-blinking continue-indicator (the non-blinking version implies this is the last page).
                    g.drawImage(Assets.pokeballToken,
                            textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                            textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                            widthLetter,
                            heightLetter,
                            null);
                }
                */

                break;
            case PAGE_OUT_ANIMATION:
                //taken care of by TEXT_AREA and BORDER (outside of this switch-construct).
                //it's outside so it can be redrawn for all cases.

                break;
            case EXIT:
                //in State.EXIT, TextboxState.tick() will pop itself off StateManager.stateStack.
                //which calls TextboxState.exit() (the exit() METHOD is currently empty).

                break;
            default:
                System.out.println("TextboxState.render(Graphics), switch-construct's default.");
                break;
        }
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void changeCurrentState(State nextState) {
        currentState = nextState;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    @Override
    public void enter(Object[] args) {
        //TextboxState is an IState that shouldn't affect the game clock.
        handler.getTimeManager().setClockRunningFalse();

        if (args != null) {
            //MESSAGE
            if (args[0] instanceof String) {
                textPassedIn = (String)args[0];
            }
            //DESTINATION
            if ( (args.length > 1) && (args[1] instanceof int[]) ) {
                locationAndSize = (int[])args[1];
            }
        }
        //check if MESSAGE is missing value.
        if (textPassedIn == null) {
            textPassedIn = "blank789message";
        }
        //check if DESTINATION is missing value.
        //TODO: ought to check if each index of locationAndSize array is missing a value rather than for the whole array.
        if (locationAndSize == null) {
            int widthDefault = ((handler.getWidth() / 10) * 8);
            int xDefault = (handler.getWidth() / 2) - (widthDefault / 2);
            int heightDefault = (handler.getHeight() / 4);
            int yDefault = handler.getHeight() - (heightDefault) - (handler.getWidth() / 10); //whole - textArea.height - margin.

            locationAndSize = new int[4];
            locationAndSize[0] = xDefault;
            locationAndSize[1] = yDefault;
            locationAndSize[2] = widthDefault;
            locationAndSize[3] = heightDefault;
        }
        /////////////////
        initTextLayout();   //takes care of args being null.
        /////////////////

        //TODO: initTextLayout() will take care of the below.
        /*
        //RESET textbox-background to its initial dimension.
        textArea.setxCurrent(textArea.getxInit());
        textArea.setyCurrent(textArea.getyInit());
        textArea.setWidthCurrent(textArea.getWidthInit());
        textArea.setHeightCurrent(textArea.getHeightInit());

        //RESET type-in-fx rectangle (for message) to cover the entire line.
        firstLine.setxTypeInFX( firstLine.getX() );
        firstLine.setyTypeInFX( firstLine.getY() );
        firstLine.setWidthTypeInFX( firstLine.getWidth() );
        firstLine.setHeightTypeInFX( firstLine.getHeight() );
        //RESET type-in-fx rectangle (for secondLine) to cover the entire line.
        secondLine.setxTypeInFX( secondLine.getX() );
        secondLine.setyTypeInFX( secondLine.getY() );
        secondLine.setWidthTypeInFX( secondLine.getWidth() );
        secondLine.setHeightTypeInFX( secondLine.getHeight() );
        */
    }

    @Override
    public void exit() {
        //DO NOT call TimeManager.setClockRunningTrue(), sometime popping TextboxState result in in-doors IState.
        //SAME for TimeManager.setClockRunningFalse(), sometime popping TextboxState result in out-doors IState.
        //NEVERMIND THE EARLIER COMMENTS, we do have to decide when to restart the game clock.
        int indexPriorIState = (handler.getStateManager().getStatesStack().size() - 2);
        IState priorIState = handler.getStateManager().getStatesStack().get(indexPriorIState);

        if ( (priorIState instanceof CrossroadState) || (priorIState instanceof GameState) ||
                (priorIState instanceof MountainState) || (priorIState instanceof TheWestState) ) {
            handler.getTimeManager().setClockRunningTrue();
        }

        //@@@@@@@@@@@@
        currentState = State.ENTER;
        textPassedIn = null;
        linesToDisplay.clear();
        indexCurrentLine = 0;
        counterBlink = 0;
        counterTypeIn = 0;
        indexTypeIn = 0;
        indexLineOnPage = 0;
        visible = false;
        locationAndSize = null;
        textArea = null;
        numberOfLinesPerPage = 0;
        numberOfLettersPerLine = 0;
        linesTemplateOfTextArea.clear();
        renderContinueIndicator = false;
        //@@@@@@@@@@@@
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ INNER-CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class TextArea {

        protected Rectangle panelInitial;
        protected Rectangle panelFinal;
        protected Rectangle panelCurrent;

        private int xInit, yInit, widthInit, heightInit;
        private int xFinal, yFinal, widthFinal, heightFinal;
        private int xCurrent, yCurrent, widthCurrent,heightCurrent;

        public TextArea(int xFinal, int yFinal, int widthFinal, int heightFinal) {
            initPanelFinalCoordinates(xFinal, yFinal, widthFinal, heightFinal);
            initPanelInitialCoordinates();
            initPanelCurrentCoordinates();
        } // **** end TextArea(int, int, int, int) constructor ****

        private void initPanelFinalCoordinates(int xFinal, int yFinal, int widthFinal, int heightFinal) {
            this.xFinal = xFinal;
            this.yFinal = yFinal;
            this.widthFinal = widthFinal;
            this.heightFinal = heightFinal;

            //check if specified dimension will cause TextArea to go off-screen, if so, adjust so it won't.
            if ( (this.xFinal + this.widthFinal) > handler.getWidth() ) {
                int widthOffscreen = handler.getWidth() - (this.xFinal + this.widthFinal);
                this.widthFinal = this.widthFinal - widthOffscreen;
            }
            if ( (this.yFinal + this.heightFinal) > handler.getHeight() ) {
                int heightOffscreen = handler.getHeight() - (this.yFinal + this.heightFinal);
                this.heightFinal = this.heightFinal - heightOffscreen;
            }

            panelFinal = new Rectangle(this.xFinal, this.yFinal, this.widthFinal, this.heightFinal);
        }

        private void initPanelInitialCoordinates() {
            int xCenter = xFinal + (widthFinal / 2);
            int yCenter = yFinal + (heightFinal / 2);

            widthInit = 2 * (widthFinal / 10);
            heightInit = 2 * (heightFinal / 10);
            xInit = xCenter - (widthInit / 2);
            yInit = yCenter - (heightInit / 2);

            panelInitial = new Rectangle(xInit, yInit, widthInit, heightInit);
        }

        private void initPanelCurrentCoordinates() {
            xCurrent = xInit;
            yCurrent = yInit;
            widthCurrent = widthInit;
            heightCurrent = heightInit;

            panelCurrent = new Rectangle(xCurrent, yCurrent, widthCurrent, heightCurrent);
        }

        // GETTERS AND SETTERS

        public void setxCurrent(int xCurrent) {
            this.xCurrent = xCurrent;
        }

        public void setyCurrent(int yCurrent) {
            this.yCurrent = yCurrent;
        }

        public void setWidthCurrent(int widthCurrent) {
            this.widthCurrent = widthCurrent;
        }

        public void setHeightCurrent(int heightCurrent) {
            this.heightCurrent = heightCurrent;
        }

        public int getxInit() {
            return xInit;
        }

        public int getyInit() {
            return yInit;
        }

        public int getWidthInit() {
            return widthInit;
        }

        public int getHeightInit() {
            return heightInit;
        }

        public int getxFinal() {
            return xFinal;
        }

        public int getyFinal() {
            return yFinal;
        }

        public int getWidthFinal() {
            return widthFinal;
        }

        public int getHeightFinal() {
            return heightFinal;
        }

        public int getxCurrent() {
            return xCurrent;
        }

        public int getyCurrent() {
            return yCurrent;
        }

        public int getWidthCurrent() {
            return widthCurrent;
        }

        public int getHeightCurrent() {
            return heightCurrent;
        }

    } // **** end TextArea inner-class ****

    class Line {

        //message.
        private String message;

        //coordinates of line.
        private int x, y, width, height;

        //coordinates of type-in effect rectangle.
        private int xTypeInFX, yTypeInFX, widthTypeInFX, heightTypeInFX;

        public Line(int xOffset, int y) {
            x = textArea.getxFinal() + xOffset;
            this.y = textArea.getyFinal() + y;
            width = textArea.getWidthFinal() - (2*xOffset) -5; //-5 just to get a specific (tester) textPassedIn to fit nicely.
            height = heightLetter;

            xTypeInFX = x;
            yTypeInFX = y;
            widthTypeInFX = width;
            heightTypeInFX = height;
        } // **** end Line(int, int) constructor ****

        //GETTERS AND SETTERS

        public int getWidth() {
            return width;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getxTypeInFX() {
            return xTypeInFX;
        }

        public void setxTypeInFX(int xTypeInFX) {
            this.xTypeInFX = xTypeInFX;
        }

        public int getyTypeInFX() {
            return yTypeInFX;
        }

        public void setyTypeInFX(int yTypeInFX) {
            this.yTypeInFX = yTypeInFX;
        }

        public int getWidthTypeInFX() {
            return widthTypeInFX;
        }

        public void setWidthTypeInFX(int widthTypeInFX) {
            this.widthTypeInFX = widthTypeInFX;
        }

        public int getHeightTypeInFX() {
            return heightTypeInFX;
        }

        public void setHeightTypeInFX(int heightTypeInFX) {
            this.heightTypeInFX = heightTypeInFX;
        }

    } // **** end Line inner-class ****

}