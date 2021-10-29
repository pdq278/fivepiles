/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepiles;
//goodbye

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

class Card extends JPanel
{
	public static enum Value
	{
		ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}

	public static enum Suit
	{
		SPADES, CLUBS, DIAMONDS, HEARTS
	}

	protected static Image cardImage; // What you see when the card is face up.
	protected static Image cardBackImage; // What you see when the card is face down.

	private Suit _suit;

	private Value _value;

	private Boolean _faceup;

	private int _id;

	private Point _location; // location relative to container

	private Point whereAmI; // used to create abs postion rectangle for contains
	// functions

	private int x; // used for relative positioning within CardStack Container
	private int y;

	private final int x_offset = 10;
	private final int y_offset = 20;
	private final int new_x_offset = x_offset + (CARD_WIDTH - 30);
	final static public int CARD_HEIGHT = 150;

	final static public int CARD_WIDTH = 100;

	final static public int CORNER_ANGLE = 25;

	public boolean moving;

	Card(Suit suit, Value value)
	{
		_suit = suit;
		_value = value;
		_faceup = false;
		_id = (int)(Math.random()*100000f);
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();
		moving = false;

		loadCardImages();
	}

	Card()
	{
		_suit = Card.Suit.CLUBS;
		_value = Card.Value.ACE;
		_faceup = false;
		_id = (int)(Math.random()*100000f);
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();

		loadCardImages();
	}

	public void loadCardImages(){
		try {
			if (cardImage == null) {
				Card.cardImage = ImageIO.read(new File("textures\\CardBack.png")).getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
			}

			if (cardBackImage == null) {
				Card.cardBackImage = ImageIO.read(new File("textures\\CardBack.png")).getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Suit getSuit()
	{
		switch (_suit)
		{
		case HEARTS:
			System.out.println("Hearts");
			break;
		case DIAMONDS:
			System.out.println("Diamonds");
			break;
		case SPADES:
			System.out.println("Spades");
			break;
		case CLUBS:
			System.out.println("Clubs");
			break;
		}
		return _suit;
	}

	/**
	 * Returns the card's ID. The cards ID is a random number generated when the card object is constructed.
	 * @return Returns the card's ID.
	 */
	public String getID(){
		return Integer.toString(_id);
	}

	public Value getValue()
	{
		switch (_value)
		{
		case ACE:
			System.out.println(" Ace");
			break;
		case TWO:
			System.out.println(" 2");
			break;
		case THREE:
			System.out.println(" 3");
			break;
		case FOUR:
			System.out.println(" 4");
			break;
		case FIVE:
			System.out.println(" 5");
			break;
		case SIX:
			System.out.println(" 6");
			break;
		case SEVEN:
			System.out.println(" 7");
			break;
		case EIGHT:
			System.out.println(" 8");
			break;
		case NINE:
			System.out.println(" 9");
			break;
		case TEN:
			System.out.println(" 10");
			break;
		case JACK:
			System.out.println(" Jack");
			break;
		case QUEEN:
			System.out.println(" Queen");
			break;
		case KING:
			System.out.println(" King");
			break;
		}
		return _value;
	}

	/**
	 * This method is meant to be used to see if the numerical values of two cards add up to 13, to achieve a score.
	 * @param value Possible choices are ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING.
	 * @return Returns a numerical value corresponding to the Value provided.
	 */
	private int numericalFromValue(Value value){
		switch (value) {
			case ACE:
				return 1;
			case TWO:
				return 2;
			case THREE:
				return 3;
			case FOUR:
				return 4;
			case FIVE:
				return 5;
			case SIX:
				return 6;
			case SEVEN:
				return 7;
			case EIGHT:
				return 8;
			case NINE:
				return 9;
			case TEN:
				return 10;
			case JACK:
				return 11;
			case QUEEN:
				return 12;
			case KING:
				return 13;
			default:
				return 0;
		}
	}

	/**
	 * This method is necessary to calculate whether the value of two cards added, results in 13.
	 * @return Returns the NUMERICAL value of the Card object.
	 */
	public int getNumericalValue(){
		return numericalFromValue(this.getValue());
	}

	public void setWhereAmI(Point p)
	{
		whereAmI = p;
	}

	public Point getWhereAmI()
	{
		return whereAmI;
	}

	public Point getXY()
	{
		return new Point(x, y);
	}

	public Boolean getFaceStatus()
	{
		return _faceup;
	}

	public void setXY(Point p)
	{
		x = p.x;
		y = p.y;

	}

	public void setSuit(Suit suit)
	{
		_suit = suit;
	}

	public void setValue(Value value)
	{
		_value = value;
	}

	public boolean isKing(){
		return this.getNumericalValue() == 13;
	}

	public Card setFaceup()
	{
		_faceup = true;
		return this;
	}

	public Card setFacedown()
	{
		_faceup = false;
		return this;
	}

	@Override
	public boolean contains(Point p)
	{
		Rectangle rect = new Rectangle(whereAmI.x, whereAmI.y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
		return (rect.contains(p));
	}

	private void drawSuit(Graphics2D g, String suit, Color color)
	{
		g.setFont(new Font("Aerial", Font.PLAIN, 30 ));
		g.setColor(color);
		g.drawString(suit, _location.x + x_offset-6, _location.y + y_offset + 28);
		g.drawString(suit, _location.x + new_x_offset-6, _location.y + CARD_HEIGHT - 5 - 23);
	}

	private void drawValue(Graphics2D g, String value)
	{
		g.setFont(new Font("Algerian", Font.PLAIN, 25 )); // This is only necessary until we add card images to replace these.
		g.drawString(value, _location.x + ((this.getNumericalValue() == 10) ? x_offset-10 : x_offset-5), _location.y + y_offset);
		g.drawString(value, _location.x + ((this.getNumericalValue() == 10) ? new_x_offset-12 : new_x_offset), _location.y + y_offset + CARD_HEIGHT - 25);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		RoundRectangle2D rect2 = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
				CORNER_ANGLE, CORNER_ANGLE);
		g2d.setColor(Color.WHITE);
		g2d.fill(rect2);
		g2d.setColor(Color.black);
		g2d.draw(rect2);
		g2d.drawImage(cardImage, _location.x, _location.y, this);

		// DRAW THE CARD SUIT AND VALUE IF FACEUP
		if (_faceup)
		{
			switch (_suit)
			{
			case HEARTS:
				drawSuit(g2d, "♥", Color.RED);
				break;
			case DIAMONDS:
				drawSuit(g2d, "◆", Color.RED);
				break;
			case SPADES:
				drawSuit(g2d, "♠", Color.BLACK);
				break;
			case CLUBS:
				drawSuit(g2d, "♣", Color.BLACK);
				break;
			}
			int new_x_offset = x_offset + (CARD_WIDTH - 30);
			switch (_value)
			{
			case ACE:
				drawValue(g2d, "A");
				break;
			case TWO:
				drawValue(g2d, "2");
				break;
			case THREE:
				drawValue(g2d, "3");
				break;
			case FOUR:
				drawValue(g2d, "4");
				break;
			case FIVE:
				drawValue(g2d, "5");
				break;
			case SIX:
				drawValue(g2d, "6");
				break;
			case SEVEN:
				drawValue(g2d, "7");
				break;
			case EIGHT:
				drawValue(g2d, "8");
				break;
			case NINE:
				drawValue(g2d, "9");
				break;
			case TEN:
				drawValue(g2d, "10");
				break;
			case JACK:
				drawValue(g2d, "J");
				break;
			case QUEEN:
				drawValue(g2d, "Q");
				break;
			case KING:
				drawValue(g2d, "K");
				break;
			}
		} else
		{
			// DRAW THE BACK OF THE CARD IF FACEDOWN
			RoundRectangle2D rect = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
					CORNER_ANGLE, CORNER_ANGLE);
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(rect);
			g2d.setColor(Color.black);
			g2d.draw(rect);
			g2d.drawImage(cardBackImage, _location.x, _location.y, this); // Include this line for a card back image.
		}

	}

}// END Card

