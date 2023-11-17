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
import android.widget.RadioGroup;
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

public class Zly101Activity extends AppCompatActivity {
    private List<Question> questionList;
    private ArrayList<Integer> arr;
    private RadioGroup mGroup;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    private Button btnNext, btnPrev, btnEnd;
    private CountDownTimer timer;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zly101);

        questionList = new ArrayList<>();
        arr = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        mGroup = findViewById(R.id.rbGroup);
        answerText = findViewById(R.id.txtAnswer);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        timer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = mTimeLeft / 1000 / 60;
                int secs = (mTimeLeft / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();


        if (FirstSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 3) {
            //getQuestionPhase3(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 4) {
            //getQuestionPhase4(questionList);

            setDataView(pos);
        }

        btnEnd.setOnClickListener(view -> dialogAlert());

        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        setListeners();
        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 30) {
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

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
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

    private void getQuestionPhase(List<Question> list) {
        questionList.add(new Question("1. Which of these characterise high temperature in water",
                "A. Thermolimnion",
                "B. Epilimnion",
                "C. Hypolimnion",
                "D. Thermocline",
                "B. Epilimnion"));

        questionList.add(new Question("2.  Plants take in nitrogen in which for",
                "A. Nitrate",
                "B. Nitrites",
                "C. Nitrogen",
                "D. Nitrogen",
                "C. Nitrogen"));

        questionList.add(new Question("3. Which is not a soil component?",
                "A. Inorganic",
                "B. Mineral",
                "C. Organic",
                "D. Soil water",
                "A. Inorganic"));

        questionList.add(new Question("4. What fixes atmospheric nitrogen?",
                "A. Azobacter",
                "B. Nitrosomonas",
                "C. Nitrobium",
                "D. Nitrobacter",
                "A. Azobacter"));

        questionList.add(new Question("5. Ecology deals with the study of",
                "A. Living beings",
                "B. Living and non-living components",
                "C. Reciprocal relationship between living and non-living components",
                "D. Environment",
                "C. Reciprocal relationship between living and non-living components"));

        questionList.add(new Question("6. The layer of soil that is light coloured or so is",
                "A. A1",
                "B. A2",
                "C. B2",
                "D. O",
                "B. A2"));

        questionList.add(new Question("7. Layer of soil that is partially decomposed is",
                "A. O1",
                "B. B3",
                "C. O2",
                "D. A2",
                "C. O2"));

        questionList.add(new Question("8. Sulphur is involved in the synthesis of the following except",
                "A. Carbohydrates",
                "B. Amino acids",
                "C. Certain enzymes",
                "D. Protein of glycosides",
                "A. Carbohydrates"));

        questionList.add(new Question("9. The atmospheric layer with highest gas density is _____",
                "A. Troposphere",
                "B. Stratosphere",
                "C. Exosphere",
                "D. Thermosphere",
                "A. Troposphere"));

        questionList.add(new Question("10. The following are problems of excessive phosphate in water except",
                "A. Plant growth",
                "B. Algae bloom",
                "C. Depletion of oxygen ",
                "D. Pollution of water",
                "A. Troposphere"));

        questionList.add(new Question("11. The concept of ecological pyramid was first proposed by",
                "A. E.P. Odum",
                "B. A.G. Tansley",
                "C. Juday",
                "D. Charles Elton",
                "D. Charles Elton"));

        questionList.add(new Question("12. The atmospheric layer closest to the earth is",
                "A. Troposphere",
                "B. Stratosphere",
                "C. Mesosphere",
                "D. Thermosphere",
                "A. Troposphere"));

        questionList.add(new Question("13. The atmospheric layer that prevents direct penetration of ultraviolet rays is",
                "A. Troposphere",
                "B. Stratosphere",
                "C. Mesosphere",
                "D. Thermosphere",
                "B. Stratosphere"));

        questionList.add(new Question("14. The type of succession in a swamp is",
                "A. Hydrach",
                "B. Psamosere",
                "C. Halosere",
                "D. Xerach",
                "A. Hydrach"));

        questionList.add(new Question("15. Which of these is not a characteristic of a climax community?",
                "A. It is stable",
                "B. It is a complex process",
                "C. It is fast process",
                "D. They perpetuate themselves by growth",
                "B. It is a complex process"));

        questionList.add(new Question("16. Which of these does not affect the growth rate",
                "A. Food",
                "B. Fecundity",
                "C. Dispersion",
                "D. Migration",
                "B. Fecundity"));

        questionList.add(new Question("17. Which of the following organisms convert nitrates to nitrogen gas",
                "A. Nitrococcus",
                "B. Pseudomonas fluorescens",
                "C. Nitrosomonas",
                "D. Nitrobacter",
                "B. Pseudomonas fluorescens"));

        questionList.add(new Question("18. Plants use carbon in the formula",
                "A. Carbon monoxide",
                "B. Carbon dioxide",
                "C. Methane",
                "D. Limestone",
                "A. Carbon monoxide"));

        questionList.add(new Question("19. The ratio of gross productivity to the amount of heat supplies or energy is called what",
                "A. net productivity",
                "B. total productivity",
                "C. all of the above",
                "D. none of the above",
                "A. net productivity"));

        questionList.add(new Question("20. Growth is achieved when",
                "A. Mortality exceeds natality",
                "B. Mortality equals natality",
                "C. Natality exceeds mortality ",
                "D. none of the above",
                "C. Natality exceeds mortality "));

        questionList.add(new Question("21. What is the correct order of arrangement of the levels of atmosphere?",
                "A. Exosphere, Troposphere, Stratosphere, Mesosphere, Thermosphere",
                "B. Troposphere, Stratosphere, Mesosphere, Thermosphere, Exosphere",
                "C. Exosphere, Troposphere, Mesosphere, Stratosphere, Thermosphere",
                "D. Troposphere, Mesosphere, Stratosphere, Thermosphere, Exosphere",
                "B. Troposphere, Stratosphere, Mesosphere, Thermosphere, Exosphere"));

        questionList.add(new Question("22. The pyramid that involves transfer of calories is",
                "A. Pyramid of numbers",
                "B. Pyramid of energy",
                "C. Pyramid of biomass ",
                "D. none of the above",
                "B. Pyramid of energy"));

        questionList.add(new Question("23. When saprophytic organism feed on decomposed matter, carbon is released to the atmosphere by",
                "A. Respiration",
                "B. Decay",
                "C. Combustion",
                "D. Photosynthesis",
                "B. Decay"));

        questionList.add(new Question("24. Plants take in water and release it by",
                "A. Evaporation",
                "B. Transpiration",
                "C. Precipitation",
                "D. Excretion",
                "B. Transpiration"));

        questionList.add(new Question("25. A group of rats form clumped dispersion, a particular species of bird feed only on the rat in what form",
                "A. Clumped dispersion",
                "B. Random dispersion",
                "B. Uniform dispersion",
                "D. None of the above",
                "A. Clumped dispersion"));

        questionList.add(new Question("26.  _________ is the component of nucleotide which serves as energy storage within cells",
                "A. Phosphorus",
                "B. Carbon",
                "C. Sulphur",
                "D. Oxygen",
                "A. Phosphorus"));

        questionList.add(new Question("27. An ecosystem inside an ecosystem is called what",
                "A. Macro ecosystem ",
                "B. Micro ecosystem",
                "C. Meso ecosystem",
                "D. Biocenosis",
                "B. Micro ecosystem"));

        questionList.add(new Question("28. Deep sea living animals have problem of",
                "A. Breathing",
                "B. Reproduction",
                "C. Feeding",
                "D. None of the above",
                "A. Breathing"));

        questionList.add(new Question("29. When Ranatta sp. is flexing its left leg muscle it is",
                "A. Chemotaxism",
                "B. Phototaxism",
                "C. Geotaxism",
                "D. Rheotaxis",
                "B. Phototaxism"));

        questionList.add(new Question("30. Reason why winged animals and birds require large wind fields",
                "A. To conserve energy",
                "B. For reproduction",
                "C. To reduce stress and physical exertion costs",
                "D. None",
                "A. To conserve energy"));

    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. The pyramid of energy in terrestrial ecosystem is",
                "A. upright",
                "B. inverted",
                "C. inverted",
                "D. irregular",
                "A. upright"));

        questionList.add(new Question("2. Which of the following ecological pyramid is always upright?",
                "A. Pyramid of energy",
                "B. Pyramid of number",
                "C. Pyramid of biomass",
                "D. none of these",
                "A. Pyramid of energy"));

        questionList.add(new Question("3. The pyramid of numbers in a single tree is",
                "A. upright",
                "B. inverted",
                "C. inverted",
                "D. none of these",
                "B. inverted"));

        questionList.add(new Question("4. The pyramid of biomass is inverted in",
                "A. forest ecosystem",
                "B. grassland ecosystem",
                "C. fresh water ecosystem",
                "D. tundra",
                "C. fresh water ecosystem"));

        questionList.add(new Question("5. The pyramid of numbers is inverted in",
                "A. forest ecosystem",
                "B. tree ecosystem",
                "C. fresh water ecosystem",
                "D. tundra",
                "B. tree ecosystem"));

        questionList.add(new Question("6. Which of the following statement is incorrect regarding ecological pyramids?",
                "A. The pyramid of energy is inverted in ocean ecosystem",
                "B. The pyramid of biomass is inverted in aquatic ecosystem",
                "C. The pyramid of numbers is upright in grass land ecosystem",
                "D. The pyramid of biomass is upright in grass land ecosystem",
                "A. The pyramid of energy is inverted in ocean ecosystem"));

        questionList.add(new Question("7. Ecology deals with the study of",
                "A. Living beings",
                "B. Living and non living components",
                "C. Reciprocal relationship between living and non living components",
                "D. Environment",
                "C. Reciprocal relationship between living and non living components"));

        questionList.add(new Question("8. Autoecology deals with",
                "A. Ecology of species",
                "B. Ecology of many species",
                "C. Ecology of community",
                "D. All the above",
                "A. Ecology of species"));

        questionList.add(new Question("9. Synecology deals with",
                "A. Ecology of many species",
                "B. Ecology of many populations",
                "C. Ecology of community",
                "D. None the above",
                "C. Ecology of community"));

        questionList.add(new Question("10. Ecotype is a type of species in which environmentally induced variations are",
                "A. Temporary",
                "B. Genetically fixed",
                "C. Genetically not related",
                "D. None the above",
                "B. Genetically fixed"));

        questionList.add(new Question("11. The term ‘Biocoenosis’ was proposed by",
                "A. Transley",
                "B. Carl Mobius",
                "C. Warming",
                "D. None the above",
                "B. Carl Mobius"));

        questionList.add(new Question("12. The pyramid of energy in any ecosystem is",
                "A. Always upright",
                "B. May be upright or invented",
                "C. Always inverted",
                "D. None the above",
                "A. Always upright"));

        questionList.add(new Question("13. Energy flow in ecosystem is",
                "A. Unidirectional",
                "B. Bidirectional",
                "C. Multidirectional",
                "D. None the above",
                "A. Unidirectional"));

        questionList.add(new Question("14. An ecosystem must have continuous external source of",
                "A. minerals",
                "B. energy",
                "C. food",
                "D. All the above",
                "B. energy"));

        questionList.add(new Question("15. The source of energy in an ecosystem is",
                "A. ATP",
                "B. Sunlight",
                "C. D.N.A",
                "D. R.N.A",
                "B. Sunlight"));

        questionList.add(new Question("16. A population is a group of",
                "A. individuals in a species",
                "B. species in a community",
                "C. communities in an ecosystem",
                "D. individuals in a family",
                "A. individuals in a species"));

        questionList.add(new Question("17. What is the most important factor for the success of animal population?",
                "A. natality",
                "B. adaptability",
                "C. unlimited food",
                "D. inter species activity",
                "B. adaptability"));

        questionList.add(new Question("18. The formula for exponential population growth is",
                "A. dN/dt = rN",
                "B. dt/dN = rN",
                "C. dN/rN = dt",
                "D. rN/dN = dt",
                "A. dN/dt = rN"));

        questionList.add(new Question("19. Human population growth curve is a",
                "A. S shaped curve",
                "B. parabola curve",
                "C. J shaped curve",
                "D. zig zag curve",
                "C. J shaped curve"));

        questionList.add(new Question("20. Exponential growth occurs when there is",
                "A. a great environmental resistance",
                "B. no environmental resistance",
                "C. no biotic potential",
                "D. a fixed carrying capacity",
                "B. no environmental resistance"));

        questionList.add(new Question("21. A human population is small, there is a greater chance of :",
                "A. gene flow",
                "B. genetic drift",
                "C. natural selection",
                "D. mutation",
                "B. genetic drift"));

        questionList.add(new Question("22. In a population, unrestricted reproductive capacity is called as",
                "A. carrying capacity",
                "B. biotic potential",
                "C. birth rate",
                "D. fertility rate",
                "C. birth rate"));

        questionList.add(new Question("23. The concept that 'population tends to increase geometrically while food supply increases " +
                "arithmetically’ was put forward by",
                "A. Adam Smith",
                "B. Charles Darwin",
                "C. Thomas Malthus",
                "D. Stuart Mill",
                "C. Thomas Malthus"));

        questionList.add(new Question("24. Two opposite forces operate in the growth and development of every population. One of them " +
                "is related to the ability to reproduce at a given rate. The force opposite to it is called",
                "A. fecundity",
                "B. mortality",
                "C. environmental resistances",
                "D. biotic control",
                "B. mortality"));

        questionList.add(new Question("25. The carrying capacity of a population is determined by its",
                "A. population growth rate",
                "B. natality",
                "C. mortality",
                "D. limiting resources",
                "D. limiting resources"));

        questionList.add(new Question("26. The pioneers in xerach succession is the",
                "A. crustose lichen",
                "B. mosses",
                "C. foliose lichen",
                "D. shrubs",
                "A. crustose lichen"));

        questionList.add(new Question("27. The final stable community in an ecological succession is called the",
                "A. final community",
                "B. ultimate community",
                "C. climax community",
                "D. seral community",
                "C. climax community"));

        questionList.add(new Question("28. The process of successful establishment of the species in a new area is called",
                "A. sere",
                "B. climax",
                "C. invasion",
                "D. ecesis",
                "D. ecesis"));

        questionList.add(new Question("29. The order of basic processes involved in succession is",
                "A. Nudation->Invasion-> competition and co action->reaction->stabilization",
                "B. Nudation->stabilization-> competition and co action->Invasion->reaction",
                "C. Invasion-> Nudation->competition and co action->Reaction->stabilization",
                "D. Invasion->stabilization-> competition and co action->Reaction->nudation",
                "A. Nudation->Invasion-> competition and co action->reaction->stabilization"));

        questionList.add(new Question("30. The formation of a climax community from an abandoned farm land is a an example of",
                "A. Autogenic succession",
                "B. allogenic successsion",
                "C. primary succession",
                "D. secondary succession",
                "D. secondary succession"));
    }
}