package net.mrjaywilson.memoryvault.controls;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class AppLabel extends Label {
	public AppLabel(String text, double width, double height) {
		
		setText(text);
		setFont(new Font("Century Gothic", 22));
		setPrefSize(width, height);

		setTextAlignment(TextAlignment.LEFT);
		setAlignment(Pos.CENTER_LEFT);
		
		// Create styling later
	}
}
