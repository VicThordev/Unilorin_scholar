package com.folahan.unilorinscholar.Activity.Questions.FirstSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinscholar.MainActivity;
import com.folahan.unilorinscholar.Models.Question;
import com.folahan.unilorinscholar.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Sta131Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sta131);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.txtAnswer);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setListeners();

        timer = new CountDownTimer(mTimeLeft,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = mTimeLeft / 1000 / 60;
                int secs = (mTimeLeft/1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();

        mTimerRunning = true;

        if (FirstSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 3) {
            getQuestionPhase3(questionList);

            setDataView(pos);
        }

        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 15) {
                Toast.makeText(this, "Last Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered++;
                pos++;
                setDataView(pos);
            }
        });

        btnPrev.setOnClickListener(view -> {
            if (questionAnswered == 1) {
                Toast.makeText(this, "First Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered--;
                pos--;
                setDataView(pos);
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        btnEnd.setOnClickListener(view -> {
            dialogAlert();
        });
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 15 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);
        Button showAnswer = bottomSheet.findViewById(R.id.btnAnswer);

        scoreShow.setText("Your score is \n"+pos2+" out of 30");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
        });

        showAnswer.setOnClickListener(view -> {
            timer.cancel();
            answerText.setVisibility(View.VISIBLE);
            rbOption1.setClickable(false);
            rbOption2.setClickable(false);
            rbOption3.setClickable(false);
            rbOption4.setClickable(false);
            btnEnd.setText(R.string.go_home);
            btnEnd.setOnClickListener(view1 -> startActivity(new Intent(this, MainActivity.class)));
            answerText.setText(R.string.log_out);
            answerText.setText(questionList.get(pos).getAnswer());
            rbOption1.setVisibility(View.GONE);
            dialog.cancel();
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(String.format("Answer: %s", questionList.get(position).getAnswer()));

        questionNo.setText("Question "+questionAnswered+" of 15");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. ______ is the science dealing with the development of scientific procedures of " +
                "collection, organization, and analysis and interpretation of a source of a statistical data.",
                "a) Statistics",
                "b) Information Analysis", "c) Project Research",
                "d) Statistic",
                "a) Statistics"));

        questionList.add(new Question("2. The kind of scale that has to do with identification is called _____",
                "a) Ordinal Scale",
                "b) Nominal Scale", "c) Ratio Scale",
                "d) Variable",
                "b) Nominal Scale"));

        questionList.add(new Question("3. ______ is a sampling method in which data are collected by chance",
                "a) Systematic sampling",
                "b) Stratified sampling",
                "c) Clustered sampling",
                "d) Random Sampling",
                "d) Random Sampling"));

        questionList.add(new Question("4. A characteristic or attribute that can assume different values is called _____",
                "a) Sample",
                "b) Real Value", "c) Variable",
                "d) Statistics",
                "c) Variable"));

        questionList.add(new Question("5. The following are measures of shapes except",
                "a) Normality",
                "b) Skewness", "c) Median",
                "d) Kurtosis",
                "c) Median"));

        questionList.add(new Question("6. ____ is a real value function whose values can be taken within a defined range.",
                "a) Random Variable",
                "b) Sample",
                "c) Parameter",
                "d) Statistics",
                "a) Random Variable"));

        questionList.add(new Question("7. Which of the following expression correctly defines Normality",
                "a) Mean = Median = Mode",
                "b) Mean > Median = Mode",
                "c) Mode < Mean = Median",
                "d) Mean < Median < Mode",
                "a) Mean = Median = Mode"));

        questionList.add(new Question("8. ____ is the number of times a certain value of class or group occur",
                "a) Data occurence",
                "b) Frequency",
                "c) Class limit", "d) Sample",
                "b) Frequency"));

        questionList.add(new Question("9. ____ is the data collected in the original form.",
                "a) Frequency",
                "b) Grouped Frequency",
                "c) Raw Data", "d) Grouped Data",
                "c) Raw Data"));

        questionList.add(new Question("10. 1,3,2,4,3,1,2,2,3,2,6,4,3,4,1,2,3,1,1,1,2,4,5,4,3,2,2,3,4,2,1,1 \n " +
                "Obtain the relative frequency of 1",
                "a) 6/34", "b) 1/34",  "c) 8/34" ,
                "d) 9/34", "c) 8/34"));

        questionList.add(new Question("11. 1,3,2,4,3,1,2,2,3,2,6,4,3,4,1,2,3,1,1 \n " +
                "This data is kind of ______",
                "a) Ungrouped data", "c) Random data",
                "c) Grouped Tally Data", "d) Managed data",
                "a) Ungrouped data"));

        questionList.add(new Question("12. _____ is the difference between the upper limit & lower limit",
                "a) Class Boundary", "b) Class mark", "c) Class Width",
                "d) Class name", "c) Class Width"));

        questionList.add(new Question("13. Which of the following is not a measure of central tendency?",
                "a) Mean", "b) Median", "c) Percentile",
                "d) Harmonic Mean", "c) Percentile"));

        questionList.add(new Question("14. Obtain the mean for the observation: 34,38,48,43,57",
                "a) 54", "b) 36.67", "c) 55", "d) 44", "d) 44"));

        questionList.add(new Question("15. The value with the highest frequency is called _______",
                "a) Mean", "b) Harmonic mean" , "c) Mode",
                "d) Median",
                "c) Mode"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. Which of the following is an exception to the possibilities of modes to have",
                "a) Geometrical Modal",
                "b) Uni-Modal",
                "c) Bi-Modal",
                "d) Multi-Modal",
                "a) Geometrical Modal"));

        questionList.add(new Question("2. _____ is a fractional part or a sub-group or subset of the entire population",
                "a) Sample",
                "b) Parameter",
                "c) Statistic",
                "d) Variable",
                "a) Sample"));

        questionList.add(new Question("3. The scale that has an additional property of no true zero is called ",
                "a) Nominal Scale",
                "b) Interval Scale",
                "c) Ordinal Scale",
                "d) Random Scale",
                "b) Interval Scale"));

        questionList.add(new Question("4. Characteristics of features obtained from population is called _____",
                "a) Sample",
                "b) Parameter",
                "c) Statistic",
                "d) Variable",
                "b) Parameter"));

        questionList.add(new Question("5. A frequency distribution in which the data is only nominal/ordinal data is ",
                "a) Ungrouped",
                "b) Categorical",
                "c) Limited",
                "d) Variance",
                "b) Categorical"));

        questionList.add(new Question("6. Suppose L1 = 14.5, Fo = 27, Fm = 25, C = 5 and C.F. = 73. " +
                "Find the Median.",
                "a) 16.4",
                "b) 23.7",
                "c) 12.6",
                "d) 13.1",
                "a) 16.4"));

        questionList.add(new Question("7. Find the Geometric Mean of the data: 3,4,6,7,8",
                "a) 5.8",
                "b) 4.2",
                "c) 5.3",
                "d) 11.4",
                "c) 5.3"));

        questionList.add(new Question("8. The following are measures of Dispersion Spread except",
                "a) Range",
                "b) Variance",
                "c) Standard Deviation",
                "d) Mean",
                "d) Mean"));

        questionList.add(new Question("9. Find the Sample Variance of the data 3,6,8,10,12,15,18",
                "a) 37.7",
                "b) 32.1",
                "c) 20.1",
                "d) 42.4",
                "a) 37.7"));

        questionList.add(new Question("10. Find the Harmonic Mean of the data: 3,4,6,7,8",
                "a) 8.3",
                "b) 4.91",
                "c) 5.3",
                "d) 12.4",
                "b) 4.91"));

        questionList.add(new Question("11. ____ is used for testing consistency/comparison",
                "a) Standard Deviation",
                "b) Class Range",
                "c) Co-efficient of Variation",
                "d) Class size",
                "c) Co-efficient of Variation"));

        questionList.add(new Question("12. The following options are methods of graphical representation of data except",
                "a) Bar Chart",
                "b) Standard Score",
                "c) Pictogram",
                "d) Pie Chart",
                "b) Standard Score"));

        questionList.add(new Question("13. Obtain the 70th percentile of the data: 3,4,6,8,9,9,10,11,11,12,13,14,15",
                "a) 11",
                "b) 14",
                "c) 10",
                "d) 9",
                "a) 11"));

        questionList.add(new Question("14. Which of the following is odd?",
                "a) 50th Percentile",
                "b) 2nd Quartile",
                "c) 5th Decile",
                "d) 90th Percentile",
                "d) 90th Percentile"));

        questionList.add(new Question("15. In a negatively skewed skewness, ",
                "a) the mean is less than the median",
                "b) the mean is greater than the median",
                "c) the mean is equal to zero",
                "d) the mean and median are equal",
                "a) the mean is less than the median"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. If PCS > zero, it implies that the",
                "a) mean is greater than the median",
                "b) median is greater than the mean",
                "c) mean is equal to median",
                "d) mean is equal to zero while median is greater than zero",
                "a) Geometrical Modal"));

        questionList.add(new Question("2. Obtain the value of PCS from the following observation 6,7,8,8,8,9,10,10,11,13",
                "a) 0.5",
                "b) 0.22",
                "c) 0.73",
                "d) 0.81",
                "c) 0.73"));

        questionList.add(new Question("3. Suppose x² = 91, y² = 487400, Ex = 21, Ey = 1560, Exy = 6520 and n = 6. " +
                "Find the rank correlation",
                "a) 0.55",
                "b) 0.62",
                "c) 0.93",
                "d) 0.89",
                "d) 0.89"));

        questionList.add(new Question("4. Suppose the Standard Deviation of an obtained data is 5.9 and Mean = 11.5. " +
                "Obtain the Co-efficient of Variation",
                "a) 51.3%",
                "b) 19.4%",
                "c) 17.4%",
                "d) 5.6%",
                "a) 51.3%"));

        questionList.add(new Question("5. ____ is the degree of linear relationship between two varible.",
                "a) Mesokurtic",
                "b) Kurtosis",
                "c) Correlation",
                "d) Variable",
                "c) Correlation"));

        questionList.add(new Question("6. A kind of statistics that deals with generalising from samples to population " +
                "using probability, performing hypothesis testing, determining relationships between variables and making predictions is _____",
                "a) Preferential",
                "b) Discrete",
                "c) Categorical",
                "d) Numerical",
                "a) Preferential"));

        questionList.add(new Question("7. Derive the range of the random data: 15,6,10,3,26,49,36",
                "a) 3",
                "b) 10",
                "c) 52",
                "d) 46",
                "d) 46"));

        questionList.add(new Question("8. Study the frequency table and obtain the Sample Variance \n" +
                "Class -> Frequency \n" +
                "0 - 4 ->  3 \n" +
                "5 - 9 ->  8 \n" +
                "10 - 14 -> 16 \n" +
                "15 - 19 -> 7 \n" +
                "20 - 24 -> 4",
                "a) 28.96",
                "b) 29.05",
                "c) 32.01",
                "d) 25.22",
                "b) 29.05"));

        questionList.add(new Question("9. Study the frequency table and obtain the 40th Percentile \n" +
                "Class -> Frequency \n" +
                "0 - 4 ->  3 \n" +
                "5 - 9 ->  8 \n" +
                "10 - 14 -> 16 \n" +
                "15 - 19 -> 7 \n" +
                "20 - 24 -> 4",
                "a) 16",
                "b) 10 - 14",
                "c) 8",
                "d) 5 - 9",
                "b) 29.74"));

        questionList.add(new Question("10. Study the frequency table and obtain the Population Variance \n" +
                "Class -> Frequency \n" +
                "0 - 4 ->  3 \n" +
                "5 - 9 ->  8 \n" +
                "10 - 14 -> 16 \n" +
                "15 - 19 -> 7 \n" +
                "20 - 24 -> 4",
                "a) 28.27",
                "b) 29.74",
                "c) 32.01",
                "d) 25.22",
                "a) 28.27"));

        questionList.add(new Question("11. Study the frequency table and obtain the Median \n" +
                "Class -> Frequency \n" +
                "0 - 4 ->  3 \n" +
                "5 - 9 ->  8 \n" +
                "10 - 14 -> 16 \n" +
                "15 - 19 -> 7 \n" +
                "20 - 24 -> 4",
                "a) 10.43",
                "b) 12.22",
                "c) 11.69",
                "d) 11.00",
                "c) 11.69"));

        questionList.add(new Question("12. Suppose σ = 9, μ = 30 and X = 25. Obtain the Z score",
                "a) 5/9",
                "b) 9/5",
                "c) -5/9",
                "d) -9/5",
                "c) -5/9"));

        questionList.add(new Question("13. Suppose n = 6, Σxy = 6520, Σx = 21, Σy = 1560, Σx² = 91, " +
                "Σy² = 1560. Obtain β",
                "a) 90",
                "b) 60.57",
                "c) 0.16",
                "d) 16.72",
                "b) 60.57"));

        questionList.add(new Question("14. Suppose n = 6, Σxy = 6520, Σx = 21, Σy = 1560, Σx² = 91, " +
                "Σy² = 1560. Obtain the rank correlation",
                "a) 0.11",
                "b) 0.45",
                "c) 0.16",
                "d) 0.89",
                "d) 0.89"));

        questionList.add(new Question("15. When the Kurtosis is greater than 3, then the kurtosis is",
                "a) Mesokurtic",
                "b) Platykurtic",
                "c) Lepthokurtic",
                "d) Kurtosis",
                "c) Lepthokurtic"));
    }
}