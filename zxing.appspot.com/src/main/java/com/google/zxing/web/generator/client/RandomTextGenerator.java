package com.google.zxing.web.generator.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;


/**
 */
public final class RandomTextGenerator  implements GeneratorSource {


    private static final String COUNT_REGEXP = "[0-9]";
    private Grid table;
    private final TextBox mPattern = new TextBox();
    private final TextBox mSeparator = new TextBox();
    private final TextBox mCount = new TextBox();

    public RandomTextGenerator(ChangeHandler handler, KeyPressHandler keyListener) {
        mPattern.addStyleName(StylesDefs.INPUT_FIELD_REQUIRED);
        mPattern.addChangeHandler(handler);
        mPattern.addKeyPressHandler(keyListener);
        mPattern.setTitle("      You can use following characters as pattern.\n\n" +
                "      c : Any Latin lower-case character\n" +
                "      C : Any Latin upper-case character\n" +
                "      n : Any digit {@code [0-9]}\n" +
                "      ! : A symbol character {@code [~`!@$%^&*()-_+= []|\\:;\"'.<>?/#,]}\n" +
                "      . : Any of the above\n" +
                "      s : A \"salt\" character {@code [A-Za-z0-9./]}\n" +
                "      b : An ASCIII character which has code from 0 to 255\n\n");
        mSeparator.addStyleName(StylesDefs.INPUT_FIELD_REQUIRED);
        mSeparator.addChangeHandler(handler);
        mSeparator.addKeyPressHandler(keyListener);
        mCount.addStyleName(StylesDefs.INPUT_FIELD_REQUIRED);
        mCount.addChangeHandler(handler);
        mCount.addKeyPressHandler(keyListener);
    }



    @Override
    public Grid getWidget() {
        if (table != null) {
            return table;
        }
        table = new Grid(3, 2);



        table.setText(0, 0, "Pattern");
        table.setWidget(0, 1, mPattern);

        mSeparator.setText(";");
        table.setText(1, 0, "Separator");
        table.setWidget(1, 1, mSeparator);
        mCount.setText("1");
        table.setText(2, 0, "Count");
        table.setWidget(2, 1, mCount);
        return table;
    }

    @Override
    public String getName() {
        return "Random text generator";
    }

    @Override
    public String getText() throws GeneratorException {
        return getRandomText();
    }

    private String getRandomText() throws GeneratorException {
        validate(mPattern);
        validate(mCount);
        String stringSeparator = mSeparator.getText();
        String stringPattern = mPattern.getText();
        String resultValue = RandomStringGenerator.generate(stringPattern);
        int t = Integer.parseInt(mCount.getText());
        for (int i = 1; i < t; i++) {
            resultValue += stringSeparator + RandomStringGenerator.generate(stringPattern);
        }
        return resultValue;
    }

    @Override
    public void validate(Widget widget) throws GeneratorException {
        if (widget == mCount) {

            String tmp = mCount.getText();
            if (!tmp.matches("^[0-9]*[1-9][0-9]*$")) {
                throw new GeneratorException("Count must be positive number");
            }
            int  t = Integer.parseInt(tmp);
            if (t < 1) {
                throw new GeneratorException("Count must be more 0");
            }
        } else if (widget == mPattern) {
            if (mPattern.getText().isEmpty()) {
                throw new GeneratorException("Pattern should be at least 1 character.");
            }
        }
    }

    @Override
    public void setFocus() {

    }
}
