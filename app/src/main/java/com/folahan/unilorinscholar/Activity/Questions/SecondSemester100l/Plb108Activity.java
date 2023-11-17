package com.folahan.unilorinscholar.Activity.Questions.SecondSemester100l;

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

public class Plb108Activity extends AppCompatActivity {

    private List<Question> questionList;
    private ArrayList<Integer> arr;
    private RadioGroup mGroup;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    private Button btnNext, btnPrev, btnEnd;
    private CountDownTimer timer;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plb108);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        btnEnd = findViewById(R.id.buttonGoto);
        answerText = findViewById(R.id.txtAnswer);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        timer = new CountDownTimer(mTimeLeft,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = (int) (mTimeLeft/1000) / 60;
                int secs = (int) (mTimeLeft/1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();

        mTimerRunning = true;

        setListeners();

        if (SecondSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 3) {
            //getQuestionPhase3(questionList);

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

        questionList.add(new Question("1. The spermatophytes are also known as __________",
                "phanerogams",
                "embryophytes",
                "land plants",
                "flowering plants",
                "phanerogams"));

        questionList.add(new Question("2. Seed-bearing plants are ______",
                "gymnosperms and pteridophytes",
                "angiosperms and bryophtes",
                "gymnosperms and angiosperms",
                "thallophytes and angiosperms",
                "gymnosperms and angiosperms"));

        questionList.add(new Question("3. Angiospermopsia are the _____",
                "flowerless plants",
                "flowering plants",
                "seedless plants",
                "vessel plants",
                "flowering plants"));

        questionList.add(new Question("4. The following genera are members of the division Gnetopsida except_____",
                "Ephedra",
                "Cycas",
                "Gnetum",
                "Welwitschia",
                "Cycas"));

        questionList.add(new Question("5. A plant that produced microspores and megaspores is known as_______",
                "Heterosporous",
                "homosporous",
                "homogenous",
                "heteropgeneous",
                "Heterosporous"));

        questionList.add(new Question("6. Land plants are also known as ____________",
                "spermatophyte",
                "magnoliopsida",
                "embryophyta",
                "Ginkgopsida",
                "embryophyta"));

        questionList.add(new Question("7. The following plant groups are divisions of gymnosperms except________",
                "Gnetopsida",
                "Magnoliopsida",
                "Pinopsida",
                "Ginkgopsida",
                "Magnoliopsida"));

        questionList.add(new Question("8. __________ are the first group of plants with edndosperm",
                "angiosperms",
                "gymnosperms",
                "pteridophytes",
                "bryophytes",
                "gymnosperms"));

        questionList.add(new Question("9. Double fertilizations in angiosperms produced _________",
                "cotyledon and endosperm",
                "zygote and endosperm",
                "embryo and endosperm",
                "seed and fruit",
                "embryo and endosperm"));

        questionList.add(new Question("10. Gymnosperms’ body form include _____________",
                "herb and shrub",
                "tree and herb",
                "herb and twig",
                "shrub and tree",
                "shrub and tree"));

        questionList.add(new Question("11. A vascular plant especially an angiosperm is basically an axis consisting of_______",
                "root and leaf",
                "shoot and root",
                "shoot and branch",
                "flower and stem",
                "shoot and root"));

        questionList.add(new Question("12. Flowers are distinguished from the ordinary shoot of the same plant by being",
                "indeterminate",
                "unstoppable",
                "determinate",
                "None of the above",
                "unstoppable"));

        questionList.add(new Question("13. The pistil of a flower consists of ___________",
                "stigma, style and ovary",
                "receptatle, style and stigma",
                "Ovary, flower stalk and style",
                "petal, septal and ovary",
                "stigma, style and ovary"));

        questionList.add(new Question("14. Petals are collectively called ___________ while sepals are known as _________",
                "calyx, corolla",
                "corolla, calyx",
                "carpel, stamen",
                "pistil, androecium",
                "corolla, calyx"));

        questionList.add(new Question("15. Seeds in conifers lie on the surface of specialized leaves called________\n " +
                "In structures called__________",
                "scales, cones",
                "cones, scales",
                "cones, ovuliferous scales",
                "ovuliferous scales, cones",
                "ovuliferous scales, cones"));

        questionList.add(new Question("16. Lateral roots develop from pericycle thus they are __________ in origin",
                "ontogenous",
                "endogenous",
                "ectogenous",
                "superficial",
                "endogenous"));

        questionList.add(new Question("17. _____________,    ________  and __________ are " +
                "the three types of apigments present in gymnosperms.",
                "carotenoids, chlorophylls and leucoplasts",
                "Elioplasts, amyloplasts, chlorophylls",
                "Carotenoids, chlorophyll A, chlorophyll B",
                "Chromoplasts, chlorophyll A, chlorophyll B",
                "Carotenoids, chlorophyll A, chlorophyll B"));

        questionList.add(new Question("18. Pinopsida is otherwise known as ___________",
                "Ginkgopsida",
                "Coniferopsida",
                "Gnetopsida",
                "Cycadopsida",
                "Coniferopsida"));

        questionList.add(new Question("19. Dioecious plants are plants with_________",
                "separate male and female flowers",
                "separate male and female plants",
                "separate sepals and petals",
                "separate microspores and megaspores",
                "separate male and female plants"));

        questionList.add(new Question("20. ___________ is the persisting body in spermatophytes",
                "Gametophyte",
                "Pteriodphyte",
                "sporophyte",
                "Bryophyte",
                "sporophyte"));

        questionList.add(new Question("21. The flowering plant is a division of spermatophyte known as_______",
                "Magnoliopsida",
                "Gymnospermopsida",
                "Cycadopsida",
                "Gnetopsida",
                "Magnoliopsida"));

        questionList.add(new Question("22. The only species of Ginkgos is __________",
                "Ginkgo beloba",
                "Ginkgo biloba",
                "Ginkgo balba",
                "Ginkgo bilobi",
                "Ginkgo biloba"));

        questionList.add(new Question("23. Pollination in gymnosperms is by ______",
                "birds",
                "animals",
                "wind",
                "water",
                "wind"));

        questionList.add(new Question("24. Male Ginkgos are planted because the females have ________",
                "poor growth",
                "dull cones",
                "nasty odour",
                "too long leaves",
                "nasty odour"));

        questionList.add(new Question("25. Which of these is not true of algae?",
                "They are thalloid",
                "They contain chlorophyll",
                "They  contain vascular tissues",
                "They are lower plants",
                "They  contain vascular tissues"));

        questionList.add(new Question("26. The study of algae is known as ",
                "Algology or Phycology",
                "Mycology or Physiology",
                "Anatomy or histology",
                "Pathology or Phycobiont",
                "Algology or Phycology"));

        questionList.add(new Question("27. Algae can exist in all these forms except ",
                "Unicellular",
                "Colonial",
                "Multicellular",
                "None of the above",
                "None of the above"));

        questionList.add(new Question("28. An anchorage device Present in algae is know as",
                "Root",
                "Rhizinae",
                "Rhizoids",
                "Rhizophere",
                "Rhizoids"));

        questionList.add(new Question("29. Paramylum is a polysaccharide found in algae related to",
                "glucose",
                "starch",
                "cellulose",
                "Glycogen",
                "starch"));

        questionList.add(new Question("30. Chrysolaminarin is a complex carbohydrate found in a group of",
                "Green algae",
                "Brown algae",
                "Blue green algae",
                "Red algae",
                "starch"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. All these are examples of flagellate unicells except",
                "Dunaliella",
                "Phacotus",
                "Chlamydomonas",
                "Tetragnium",
                "Tetragnium"));

        questionList.add(new Question("2. Gonium is a type of algae with ",
                "Plate-like colony",
                "Spherical shape",
                "Plates of cells",
                "None of the above",
                "Plate-like colony"));

        questionList.add(new Question("3. The fungal component of a lichen is best described as",
                "Phycobiont",
                "Mycobiont",
                "Fungi",
                "Algae",
                "Mycobiont"));

        questionList.add(new Question("4. A group of lichen without an organized structure is known as",
                "Foliose",
                "Leprose",
                "fructicose",
                "crustose",
                "Leprose"));

        questionList.add(new Question("5. A type of lichen structure which is much branched and can" +
                " either be upright or hanging down is known as",
                "crustose",
                "foliose",
                "fructicose",
                "crustose",
                "fruticose"));

        questionList.add(new Question("6. The study of fungi is termed",
                "funcology",
                "ecology",
                "mycology",
                "phycology",
                "mycology"));

        questionList.add(new Question("7. Fungi are classified into how many classes?",
                "8",
                "6",
                "3",
                "4",
                "4"));

        questionList.add(new Question("8. A fungus comprising a naked slimy mass of protoplasm containing" +
                " numerous nuclei is better called ",
                "spore mass",
                "mycelium",
                "plasmodium",
                "amoeboid",
                "plasmodium"));

        questionList.add(new Question("9. When a fungal mycelium is sparated into individual cells by cross walls," +
                " the fungus is said to be ",
                "aseptate",
                "septate",
                "cross walled",
                "coenocytic",
                "septate"));

        questionList.add(new Question("10. The dimensions of a fungal mycelium are not fixed " +
                "and may be affected by ",
                "temperature",
                "nutrients",
                "aeration",
                "none of the above",
                "nutrients"));

        questionList.add(new Question("11. Specialized propagative bodies made up of one or " +
                "a few cells are called ",
                "spores",
                "ciliospores",
                "asci",
                "pycnidia",
                "spores"));

        questionList.add(new Question("12. When terminal or intercalary cells cut off and round up, they form ",
                "hectospores",
                "teliospores",
                "chlamydospores",
                "aneurospores",
                "chlamydospores"));

        questionList.add(new Question("13. Flagellatd spores are also called ",
                "sporangia",
                "zoospores",
                "phialides",
                "None",
                "zoospores"));

        questionList.add(new Question("14. Asexual spores produced by the cutting off of cells from" +
                " special hypha are called",
                "conidiospores",
                "conidia",
                "idiospores",
                "spermatia",
                "conidia"));

        questionList.add(new Question("15. In the class Ascomycetes the ascospores are usually produced in what number?",
                "4",
                "6",
                "8",
                "10",
                "8"));

        questionList.add(new Question("16. In the class Basidiomycetes, the spores are called ________________ and " +
                " are usually____________________ in number?",
                "basidium; 8",
                "biospores; 4",
                "phycospores; 4",
                "basidiospors; 4",
                "basidiospors; 4"));

        questionList.add(new Question("17. A dikaryotic cell is most likely to have how many nuclei?",
                "3",
                "2",
                "4",
                "numerous",
                "2"));

        questionList.add(new Question("18. Fungi generally lack chlorophyll and are therefore",
                "autotrophs",
                "scarengers",
                "heterotrophs",
                "idiotrophs",
                "heterotrophs"));

        questionList.add(new Question("19. The fungi imperfecti group is so called because",
                "members are deformed",
                "members are unrelated",
                "members lack sexuality",
                "members die out",
                "members lack sexuality"));

        questionList.add(new Question("20. Mushrooms and toadstools are differentiated based on ",
                "classification",
                "habitat",
                "edibility",
                "life cycle",
                "edibility"));

        questionList.add(new Question("21. Between Amanita and Agaricus species the difference lies in the presence of" +
                " ________________ in which of them?",
                "stipe in Amanita",
                "volva in Agaricus",
                "volva in Amanita",
                "annulus in Agaricus",
                "volva in Amanita"));

        questionList.add(new Question("22. In mushrooms, the fruiting body growing upward above ground is called",
                "basidium",
                "ascocarp",
                "basidiocarp",
                "fertile body",
                "basidiocarp"));

        questionList.add(new Question("23. All members of the Kingdom fungi reproduce both" +
                " sexually and asexually ",
                "True",
                "False",
                "True and False",
                "None",
                "False"));

        questionList.add(new Question("24. Most mushrooms are basidiomycetes that produce haploid " +
                "basidiospores in sacs under the mushroom cap",
                "True",
                "False",
                "True and False",
                "None",
                "True"));

        questionList.add(new Question("25. Asci in Ascomycetes and basidiospores in " +
                "Basidiomycetes perform the same function",
                "True",
                "False",
                "True and False",
                "None",
                "False"));

        questionList.add(new Question("26. Conidia are reproductive cells that function during the asexual life cycles" +
                " of Ascomycetes and imperfect fungi",
                "True",
                "False",
                "True and False",
                "None",
                "True"));

        questionList.add(new Question("27. Basidiospores are _____________________ cells that germinate to directly form " +
                "_______________.",
                "haploid; mycelia",
                "haploid; asci",
                "diploid; basidia",
                "diploid; mushrooms",
                "haploid; mycelia"));

        questionList.add(new Question("28. Asexual life cycle in fungi is likely to be completed ",
                "once a season",
                "twice in a season",
                "several times",
                "none of the above",
                "twice in a season"));

        questionList.add(new Question("29. When two fertilized nuclei remain together in pairs within the cell" +
                " without actually fusing the cell is called",
                "a eucaryon",
                "a dikaryon",
                "germtube",
                "zygote",
                "a dikaryon"));

        questionList.add(new Question("30. The role of fungi in mycorrhizal association is to",
                "produce necessary nutrient",
                "increase absorptive surface for plant roots",
                "decompose",
                "synthesize antibiotics",
                "increase absorptive surface for plant roots"));
    }

}