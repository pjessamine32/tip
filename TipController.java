package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;


public class TipController {
	// format currency and percent using local currency
	private final static NumberFormat currency = NumberFormat.getCurrencyInstance();
	private final static NumberFormat percent = NumberFormat.getPercentInstance();

	private BigDecimal tipPercentage = new BigDecimal(0.15); // 15% default
	@FXML
	private TextField amountTextField;
	@FXML
	private Label tipPercentageLabel;
	@FXML
	private Slider tipPercentageSlider;
	@FXML
	private TextField tipTextField;
	@FXML
	private TextField totalTextField;

	@FXML
	private void calculateButtonPressed(ActionEvent event) {
		figureTotal();
	}

	//called by FXMLLoader to initailze the controller
	public void initialize()  {
		currency.setRoundingMode(RoundingMode.HALF_UP);

		//listener for changes to tipPercentageSlider's value
		tipPercentageSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> ov,
                Number oldValue, Number newValue) {
				tipPercentage = BigDecimal.valueOf(newValue.intValue()/100.0);
				tipPercentageLabel.setText(percent.format(tipPercentage));
				figureTotal();
           }
        });
	}
	
	private void figureTotal(){
		try {
			BigDecimal amount = new BigDecimal(amountTextField.getText());
			BigDecimal tip = amount.multiply(tipPercentage);
			BigDecimal total = amount.add(tip);

			tipTextField.setText(currency.format(tip));
			totalTextField.setText(currency.format(total));
		} catch (NumberFormatException ex) {
			amountTextField.setText("Enter Amount, please");
			amountTextField.selectAll();
			amountTextField.requestFocus();
		}

	}
}