package com.example.signalflowgraphs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Output {

    @FXML
    private TextArea FP;

    @FXML
    private TextArea gainFP;

    @FXML
    private TextArea FBL;

    @FXML
    private TextArea gainFBL;

    @FXML
    private TextArea NTL;

    @FXML
    private TextArea gainNTL;

    @FXML
    private Label overAllValue;

    public void displayOutput(String FP_, String gainFP_,
                              String FBL_, String gainFBL_,
                              String NTL_, String gainNTL_,
                              String overAllValue_) {
        FP.setText(FP_);
        gainFP.setText(gainFP_);

        FBL.setText(FBL_);
        gainFBL.setText(gainFBL_);

        NTL.setText(NTL_);
        gainNTL.setText(gainNTL_);

        overAllValue.setText(overAllValue_);

    }
}
