package edu.pooh.tiles;

import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class SignPostTile extends SolidGenericTile
        implements IInvokable {

    private String message;

    public SignPostTile(BufferedImage texture, String message) {
        super(texture);

        this.message = message;
    } // **** end SignPostTile(BufferedImage) constructor ****

    @Override
    public void execute() {
        // TODO: implement rendering/displaying the message.

        System.out.println("SignPostTile execute() method called");
    }

} //  **** end SignPostTile class ****