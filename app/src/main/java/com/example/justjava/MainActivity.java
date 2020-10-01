package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.justjava.R.id.quantity_text_view;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this , "You cannot order more than 100 coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this, "You cannot order 0 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name  = nameField.getText().toString();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_Cream_check_box);
        boolean hasWhippedCream =  whippedCreamCheckBox.isChecked();

        String priceMessage = createOrderSummary(calculatePrice(hasWhippedCream , hasChocolate) , hasWhippedCream , hasChocolate , name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT , "Just Java order for " +name);
        intent.putExtra(Intent.EXTRA_TEXT , priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String createOrderSummary(int price , boolean hasWhippedCream , boolean hasChocolate , String name){
        String priceMessage = "Name : " + name;
        priceMessage += "\nHasWhippedCream : " + hasWhippedCream;
        priceMessage += "\nHasChocolate : " + hasChocolate;
        priceMessage += "\nQuantity :" + quantity;
        priceMessage += "\nTotal price : Rs" + price;
        priceMessage += "\nThankYou!";

        return priceMessage;
    }
    private int calculatePrice(boolean hasWhippedCream , boolean hasChocolate){
        int basePrice = 5;

        if(hasChocolate){
            basePrice += 2;
        }
        if(hasWhippedCream){
            basePrice += 1;
        }
        return quantity * basePrice;
    }
}
