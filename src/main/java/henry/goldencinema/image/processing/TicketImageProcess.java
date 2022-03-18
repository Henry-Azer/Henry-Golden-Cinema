package henry.goldencinema.image.processing;

import henry.goldencinema.entity.cinema.Ticket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TicketImageProcess {

    private static BufferedImage ticketImage;
    private static final int PIXELS_PER_POINT = 4;
    private static final String BLANK_TICKET_IMG_URL = "https://i.ibb.co/wLrJ9BH/Requirements-08.png";

    public static byte[] generateTicketImage(Ticket ticket) {
        try {
            ticketImage = ImageIO.read(new URL(BLANK_TICKET_IMG_URL));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Color titleColor = new Color(229, 137, 10);
        Font titleFont = new Font("Century Gothic Bold", Font.PLAIN, toPixels(9));

        Color subTitleColor = new Color(58, 58, 58);
        Font subTitleFont = new Font("Century Gothic Regular", Font.PLAIN, toPixels(6));

        String movieTitle = ticket.getMovie().getTitle();
        int titleSplitIndex = splitTitleAfterSecondSpaceIndex(movieTitle);
        insertTextOnImage(ticketImage, 80, 110, movieTitle.substring(0, titleSplitIndex), titleColor, titleFont);
        insertTextOnImage(ticketImage, 80, 160, movieTitle.substring(titleSplitIndex + 1), titleColor, titleFont);

        insertTextOnImage(ticketImage, 80, 250, "Date: ", subTitleColor, subTitleFont);
        insertTextOnImage(ticketImage, 150, 250, ticket.getDate().toString(), subTitleColor, subTitleFont);

        String timePattern = "h:mm a";
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timePattern);
        Instant instant = LocalDateTime.of(ticket.getDate(), ticket.getTime()).atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        String movieTime = simpleTimeFormat.format(date);
        insertTextOnImage(ticketImage, 80, 320, "Time: ", subTitleColor, subTitleFont);
        insertTextOnImage(ticketImage, 150, 320, movieTime, subTitleColor, subTitleFont);

        String movieHallAndSeat = ticket.getHall().getName() + " - " + ticket.getSeat();
        insertTextOnImage(ticketImage, 320, 250, "Hall - Seat: ", subTitleColor, subTitleFont);
        insertTextOnImage(ticketImage, 450, 250, movieHallAndSeat, subTitleColor, subTitleFont);

        String movieDuration = ticket.getMovie().getDuration().toString() + " Min";
        insertTextOnImage(ticketImage, 320, 320, "Duration: ", subTitleColor, subTitleFont);
        insertTextOnImage(ticketImage, 450, 320, movieDuration, subTitleColor, subTitleFont);

        return bufferedImageToByteArray(ticketImage);
    }

    private static void insertTextOnImage(BufferedImage image, int xPosition, int yPosition, String text, Color fontColor, Font font) {
        BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);
        graphics2D.setPaint(fontColor);
        graphics2D.setFont(font);
        graphics2D.drawString(text, xPosition, yPosition);
        graphics2D.dispose();
    }

    private static int splitTitleAfterSecondSpaceIndex(String text) {
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ')
                counter++;

            if (text.charAt(i) == ' ' && counter == 2)
                return i;
        }

        return 0;
    }

    private static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    private static int toPixels(int value) {
        return value * PIXELS_PER_POINT;
    }

}
