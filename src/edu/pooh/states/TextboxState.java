package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.FontGrabber;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TextboxState implements IState {

    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }
    public enum LineNumber { ONE, TWO; }

    private Handler handler;

    private State currentState;

    // MESSAGE
    private String textPassedIn;
    private ArrayList<String> lines;    //each element is a line-length-chunk of textPassedIn.
    private int currentLine1Index = 0;  //index of lines ArrayList<String>, used for assigning firstLine.message.
    private int currentLine2Index = 1;  //index of lines ArrayList<String>, used for assigning secondLine.message.

    private int widthLetter, heightLetter; //size of each letter.
    private int xOffset;

    // TEXT_AREA (panel and border)
    private TextArea textArea;

    // LINES
    private Line firstLine;
    private Line secondLine;

    public TextboxState(Handler handler) {
        lines = new ArrayList<String>();

        this.handler = handler;
        currentState = null;

        widthLetter = 10;
        heightLetter = 10;
        xOffset = 20;

        textArea = new TextArea(xOffset);

        firstLine = new Line(LineNumber.ONE, xOffset);
        secondLine = new Line(LineNumber.TWO, xOffset);
    } // **** end TextboxState(Handler) constructor ****

    private void initTextLayout() {
        int numberOfLetterPerLine = firstLine.getWidth() / widthLetter;
        System.out.println("NUMBER OF LETTERS PER LINE: " + numberOfLetterPerLine);

        if (textPassedIn == null) {
            firstLine.setMessage("blank789message");
            secondLine.setMessage("");
            return;
        }

        //TODO: if the entire-textPassedIn-to-be-displayed is less than one line, we'll end up with ZERO numberOfPages!!!
        //if it's at-least one-line worth of textPassedIn, we'll be okay.

        StringBuilder sb = new StringBuilder();
        String[] words = textPassedIn.split(" ");
        int currentIndex = 0;


        //for (int i = 0; i < words.length; i++) {
        //    System.out.println(words[i]);
        //}

        while (currentIndex < words.length) {
            //word will fit on this line.
            if ((sb.toString().length() + words[currentIndex].length() + 1) <= numberOfLetterPerLine) { //+1 for SPACE added after.
                sb.append(words[currentIndex]).append(" ");
                currentIndex++;
            }
            //store the line-worth of textPassedIn into the lines ArrayList<String>.
            else {
                lines.add(sb.toString());
                sb.delete(0, sb.length());
            }

            //store the last line (not words.length-1 because we add a space after).
            if (currentIndex == words.length) {
                lines.add(sb.toString());
            }
        }

        //for (String line : lines) {
        //    System.out.println(line);
        //}

        //initialize message (and possibly secondLine) using currentLine#Index with textAfterLayout (each element in
        //this String array is a portion of the entire-textPassedIn-to-be-displayed that will fit on one line).
        firstLine.setMessage(lines.get(currentLine1Index));
        if (currentLine2Index < lines.size()) {
            secondLine.setMessage(lines.get(currentLine2Index));
        }
        //secondLine does not exist.
        else {
            secondLine.setMessage("");
        }

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
                if ( (textArea.getxCurrent() == textArea.getxFinal()) &&
                        (textArea.getWidthCurrent() == textArea.getWidthFinal()) &&
                        (textArea.getHeightCurrent() == textArea.getHeightFinal()) ) {
                    changeCurrentState(State.LINE_IN_ANIMATION);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case LINE_IN_ANIMATION:
                //TODO: implement animationfx of line being "typed-in".
                //int textSpeed = 2; //actual in-game textSpeed.
                int textSpeed = 10; //developer-mode textSpeed.
                //reveal the lines of textPassedIn by shrinking the covering-rectangle-that's-the-same-color-as-textbox-background.
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

                break;
            case WAIT_FOR_INPUT:
                //CHECK IF THERE'S ANOTHER PAGE: so if, continue-indicator should blink on-and-off.
                if ( (currentLine1Index + 2) < lines.size() ) {
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

                //a-button (if there's another page: set message/secondLine to their next String from the array of lines).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //IF THERE'S ANOTHER PAGE: increment the currentLine#Index and re-assign message.
                    if ( (currentLine1Index + 2) < lines.size() ) {
                        System.out.println("TextboxState.tick(), switch.WAIT_FOR_INPUT: ArrayList<String> lines has size() == " + lines.size());

                        currentLine1Index = currentLine1Index + 2;
                        firstLine.setMessage( lines.get(currentLine1Index) );
                        //CHECK IF ANOTHER secondLine exist.
                        if ( (currentLine2Index + 2) < lines.size() ) {
                            currentLine2Index = currentLine2Index + 2;
                            secondLine.setMessage( lines.get(currentLine2Index) );
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
                        //if reached this line: message's currentLine1Index+2 is too big (no more lines).
                        firstLine.setMessage( "" );
                        secondLine.setMessage( "" );

                        ////////////////////////////////////////////
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        ////////////////////////////////////////////
                    }
                }

                break;
            case PAGE_OUT_ANIMATION:
                //TEXT_AREA SHRINKING EFFECT.
                if (textArea.getxCurrent() < textArea.getxInit()) {
                    textArea.setxCurrent(textArea.getxCurrent() + 5);
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
                if ( (textArea.getWidthCurrent() == 0) && (textArea.getHeightCurrent() == 0) ) {
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
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        //repaint the render(Graphics) of the IState that is just below the top of the stack.
        handler.getStateManager().getStatesStack().get(handler.getStateManager().getStatesStack().size()-2).render(g);

        //TEXT_AREA
        g.setColor(Color.BLUE);
        g.fillRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());
        //BORDER
        g.setColor(Color.YELLOW);
        g.drawRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());

        switch (currentState) {
            case ENTER:
                //taken care of by TEXT_AREA and BORDER (outside of this switch-construct).
                //it's outside so it can be redrawn for all cases.

                break;
            case LINE_IN_ANIMATION:
                //render: message
                FontGrabber.renderString(g, firstLine.getMessage(), firstLine.getX(), firstLine.getY(), widthLetter, heightLetter);
                //render: secondLine
                //does NOT equals.
                if ( !secondLine.getMessage().equals( "" ) ) {
                    FontGrabber.renderString(g, secondLine.getMessage(), secondLine.getX(), secondLine.getY(), widthLetter, heightLetter);
                }

                //TYPE-IN EFFECT (rectangles that covers message and secondLine, and reveals them by shrinking)
                //g.setColor(Color.BLUE);
                g.setColor(Color.BLACK);
                g.fillRect(firstLine.getxTypeInFX(), firstLine.getyTypeInFX(), firstLine.getWidthTypeInFX(), firstLine.getHeightTypeInFX());
                g.fillRect(secondLine.getxTypeInFX(), secondLine.getyTypeInFX(), secondLine.getWidthTypeInFX(), secondLine.getHeightTypeInFX());

                break;
            case WAIT_FOR_INPUT:
                //render: message
                FontGrabber.renderString(g, firstLine.getMessage(), firstLine.getX(), firstLine.getY(), widthLetter, heightLetter);

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

    private void changeCurrentState(State nextState) {
        currentState = nextState;
    }

    @Override
    public void enter(Object[] args) {
        ///////////////////////////
        currentState = State.ENTER;
        ///////////////////////////

        //@@@@@@@@@@@@
        lines.clear();
        //@@@@@@@@@@@@

        //@@@IF String WAS PASSED IN, split into line-length chunks and store those chunks in an ArrayList<String> lines.
        if (args != null) {
            if (args[0] instanceof String) {
                textPassedIn = (String)args[0];
            }
        }
        /////////////////
        initTextLayout();   //takes care of args being null.
        /////////////////

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

        //RESET continue-indicator variables.
        renderContinueIndicator = false;

        //RESET currentLine#Index.
        currentLine1Index = 0;
        currentLine2Index = 1;
    }

    @Override
    public void exit() {

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

        public TextArea(int xOffset) {
            initPanelInitialCoordinates(xOffset);
            initPanelFinalCoordinates(xOffset);
            initPanelCurrentCoordinates();
        } // **** end TextArea(int) constructor ****

        private void initPanelInitialCoordinates(int xOffset) {
            xInit = (handler.getWidth() / 2) - xOffset;
            yInit = (handler.getHeight() / 2) + 20;
            widthInit = 2 * xOffset;
            heightInit = xOffset;

            panelInitial = new Rectangle(xInit, yInit, widthInit, heightInit);
        }

        private void initPanelFinalCoordinates(int xOffset) {
            xFinal = 30;
            yFinal = yInit;
            widthFinal = handler.getWidth() - (2 * xFinal);
            heightFinal = 9 * xOffset;

            panelFinal = new Rectangle(xFinal, yFinal, widthFinal, heightFinal);
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

        public Line(LineNumber lineNumber, int xOffset) {
            x = textArea.getxFinal() + xOffset;
            y = textArea.getyFinal() + xOffset;
            width = textArea.getWidthFinal() - (2*xOffset) -5; //-5 just to get a specific (tester) textPassedIn to fit nicely.
            height = heightLetter;

            if (lineNumber == LineNumber.TWO) {
                y = y + 20; //+20 for height of message AND space between message and secondLine.
            }

            xTypeInFX = x;
            yTypeInFX = y;
            widthTypeInFX = width;
            heightTypeInFX = height;
        } // **** end Line(LineNumber, int) constructor ****

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