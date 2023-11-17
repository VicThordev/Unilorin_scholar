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
import java.util.Random;

public class Plb101Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plb101);

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
        } else if (FirstSemesterActivity.questionRequestCode == 4) {
            getQuestionPhase4(questionList);
            setDataView(pos);
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 35) {
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
            rbOption1.setVisibility(View.GONE);
            rbOption2.setVisibility(View.GONE);
            rbOption3.setVisibility(View.GONE);
            rbOption4.setVisibility(View.GONE);
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

        questionNo.setText("Question "+questionAnswered+" of 35");


    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+questionAnswered+" out of 35 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. What is the function of the plastid ____",
                "A. for photosynthesis and storage",
                "B. Transport and movement of material",
                "C. Extrection in plant",
                "D. Formation of pigment",
                "A. for photosynthesis and storage"));

        questionList.add(new Question("2. Movement of materials in a plant is called ....",
                "A. Transportation",
                "B. Translocation",
                "C. Transformation",
                "D. Transpiration",
                "A. Transportation"));

        questionList.add(new Question("3. Phloem loading involves ",
                "A. Diffusion",
                "B. Osmosis",
                "C. Draining",
                "D. Xylem",
                "A. Diffusion"));

        questionList.add(new Question("4. Plants conduct water from their root through ",
                "A. Diffusion",
                "B. Osmosis",
                "C. Conduction",
                "D. Capillarity",
                "B. Osmosis"));

        questionList.add(new Question("5. Cellular respiration occurs in cells by ",
                "A. Diffusion",
                "B. Osmosis",
                "C. None of the options",
                "D. All of the above",
                "C. None of the options"));

        questionList.add(new Question("6. NAD+ is reduced to",
                "A. NAD",
                "B. NADH2",
                "C. (NADH)2" ,
                "D. NA",
                "B. NADH2"));

        questionList.add(new Question("7. FADH is oxidized to ",
                "A. FAD-", "(B) FAD₂+",
                "(C) FAD₂-", "(D) FAD+",
                "(D) FAD+"));

        questionList.add(new Question("7. The no of ATP produced in Glycolysis is",
                "A. 1",
                "B. 6",
                "C. 2",
                "d. 4",
                "C. 2"));

        questionList.add(new Question("9. Water in individual cell is about ",
                "A. 80%",
                "B. 100%",
                "C. 37%",
                "D. 52%",
                "A. 80%"));

        questionList.add(new Question("10. When liquid water freezes, the molecules are loosely packed to",
                "A. Increase density",
                "B. Reduce volume",
                "C. Reduce density",
                "D. None of the options", "C. Reduce density"));

        questionList.add(new Question("11. Which of the following is not an inherent property of water",
                "A. Thermal capacity",
                "B. Universal solvent",
                "C. Freezing property",
                "D. None of the options",
                "D. None of the options"));

        questionList.add(new Question( "12. Solid wastes are excreted out of the plant through",
                "A. Leaves only",
                "B. Leaves and bark",
                "C. Bark",
                "D. Root",
                "B. Leaves and bark"));

        questionList.add(new Question( "13. Which of the following is not double membrane?",
                "A. Chloroplast",
                "B. Mitochondria",
                "C. Golgi body",
                "D. Nucleus",
                "C. Golgi body"));

        questionList.add(new Question( "14. Carboxylation does not occur in _____",
                "A. ETC",
                "B. None is correct",
                "C. Kreb's cycle",
                "D. EMP",
                "D. EMP"));

        questionList.add(new Question( "15. At what phase of mieosis does chromosomal abberation/non-disjunction occur",
                "A. prophase1",
                "B. Metaphase1",
                "C. Anaphase1",
                "D. telophase1",
                "C. Anaphase1"));

        questionList.add(new Question( "16. Aerobic respiration occurs in the cytoplasm of_____",
                "A. Animals",
                "B. Plants",
                "C. Prokaryotes",
                "D. None of the above",
                "C. Prokaryotes"));

        questionList.add(new Question( "17. Which of the following is an obligate anaerobe",
                "A. Saccaromyce",
                "B. Bacillus",
                "C. Escerishia Coli",
                "D. All of the above",
                "C. Escerishia Coli"));

        questionList.add(new Question( "18. What part of light is used by higher plant?",
                "A. Red",
                "B. Blue",
                "C. Green",
                "D. White",
                "A. Red"));

        questionList.add(new Question( "19. The deeply coloured part of the chromosome is called ________",
                "A. Centromere",
                "B. Kinetochore",
                "C. Euchromatin",
                "D. Heterochromatin",
                "D. Heterochromatin"));

        questionList.add(new Question( " 20. The more the value of the pH is above 7 the greater its ____",
                "A. Alkalinity",
                "B. Acidity",
                "C. Neutrality",
                "D. Hydrogen ion concentration",
                "(A) hybridizing."));

        questionList.add(new Question( "21. The O2 released during photosynthesis is from",
                "A. H2O",
                "B. CO2",
                "C. Chlorophyll",
                "D. C6H12O6",
                "A. H2O"));

        questionList.add(new Question( "22. In which stage of cell division does chromosomal aberration occur?",
                "A. Prophase",
                "B. Anaphase",
                "C. Metaphase",
                "D. Telophase",
                "C. Metaphase"));

        questionList.add(new Question( "23. The meaning of FAD is ________",
                "A. Flavin Adenosine Dinucleotide",
                "B. Flavin Adenine Dinucleotide",
                "C. Flavio Adenine Dinucleotide",
                "D. Flavo Adenosine Dinucleotide",
                "B. Flavin Adenine Dinucleotide"));

        questionList.add(new Question( "24. Partial/non disjunction occurs in which phase?",
                "A. Anaphase",
                "B. Metaphase",
                "C. Telophase",
                "D. Prophase",
                "A. Anaphase"));

        questionList.add(new Question( "25. How many NADH are used up during Kreb's cycle?",
                "A. 6",
                "B. 3",
                "C. 2",
                "D. 1",
                "B. 3"));

        questionList.add(new Question( "26. Phloem loading in sugar sources occurs through which process?",
                "A. Osmosis",
                "B. Active transport",
                "C. Diffusion",
                "D. Translocation",
                "D. Translocation"));

        questionList.add(new Question( "27. Which of the following statement is true of H2O",
                "A. The 80% of the cell is made of water",
                "B. The brain is made of 78% water",
                "C. Both of the above",
                "D. None of the above",
                "C. Both of the above"));

        questionList.add(new Question( "28. Golgi apparatus comprise ____ & ____",
                "A. Golgi cisternae & vessicle",
                "A. Golgi body & vessicle",
                "C. Golgi body & cisternae",
                "D. Cristae & vessicle",
                "C. Golgi body & cisternae"));

        questionList.add(new Question( "29. The innermost part of the bark of plants is_____",
                "A. Phloem",
                "B. Xylem",
                "C. Vascular bundles",
                "D. Companion cells",
                "A. Phloem"));

        questionList.add(new Question( "30. The absorption of water by the root hairs of plant from the soil is through",
                "A. Osmosis",
                "B. Diffusion",
                "C. Active transport",
                "D. All of the above",
                "A. Osmosis"));

        questionList.add(new Question( "31. One of the following arrangements of the reactions of enzymes is correct",
                "A. Oxido-reductases,hydrolyases,transferases,isomerases,ligases,lyases ",
                "B. Oxido-reductases,transferases,hydrolyases,ligases,isomerases,lyases",
                "C. Oxido-reductases,transferases,hydrolyases,lyases,isomerases,ligases",
                "D. oxido-reductases,transferases,hydolyases,lyases,ligases,isomerases",
                "C. Oxido-reductases,transferases,hydrolyases,lyases,isomerases,ligases"));

        questionList.add(new Question( "32. Enzymes ______",
                "A. increase the rate of reactions by decreasing the activation energy",
                "B. increase the rate of reactions by decreasing by increasing the activation energy",
                "C. decrease the activation energy to decrease the rate of reactions",
                "D. none of the above",
                "A. increase the rate of reactions by decreasing the activation energy"));

        questionList.add(new Question( "33. The catalytic power of enzymes comes from _____",
                "A. Their capacity to alter reactions",
                "B. their thermolability",
                "C. their proteinous nature",
                "D. their ability to bind substrates",
                "A. Their capacity to alter reactions"));

        questionList.add(new Question( "34. The enzyme that act on the decomposition of urea/fat is ____",
                "A. Esterase",
                "B. urase",
                "C. uraese",
                "D. urease",
                "A. Their capacity to alter reactions"));

        questionList.add(new Question( "35. Which of the following enzyme reactions is carried out by the" +
                " digestive enzymes?",
                "A. ligases",
                "B. transferases",
                "C. hydolyases",
                "D. lyases",
                "C. hydolyases"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. Phloem transports",
                "A. Upwards",
                "B. Downwards",
                "C. Bi-Directional",
                "D. None of the above",
                "C. Bi-Directional"));

        questionList.add(new Question("2. Germ line of continuity was coined by",
                "A. Darwin",
                "B. Weissman",
                "C. Mendel",
                "D. Hooke",
                "B. Weissman"));

        questionList.add(new Question("3. Which of the following is not among the xylem vessels?",
                "A. Tracheids",
                "B. Parenchyma cells",
                "C. Sclerides",
                "D. Xylem elements",
                "A. Tracheids"));

        questionList.add(new Question("4. The term protoplasm was first used by",
                "A. R.Hookes",
                "B. Purkinje",
                "C. Virchow",
                "D. Baere",
                "B. Purkinje"));

        questionList.add(new Question("5. A chromosome attached to another chromosome is called",
                "A. Satellite chromosome",
                "B. Metacentric",
                "C. Acrocentric",
                "D. Sub-metacentic",
                "A. Satellite chromosome"));

        questionList.add(new Question("6. The earth is made up of what percent water?",
                "A. 60-70%",
                "B. 70-80%",
                "C. 80-90%",
                "D. 50-60%",
                "B. 70-80%"));

        questionList.add(new Question("7. The human body is made up of how much water?",
                "A. 60%",
                "B. 70%",
                "C. 80%",
                "D. 50%",
                "A. 60%"));

        questionList.add(new Question("8. The intermediate product of glycolysis is",
                "A. Glucose",
                "B. Fructose",
                "C. Glucose-6-phosphate",
                "D. Fructose-1,6-diphosphate",
                "D. Fructose-1,6-diphosphate"));

        questionList.add(new Question("9. The following materials can be transported in plants and animals excep",
                "A. Hormones",
                "B. Urea",
                "C. CO2",
                "D. Oxygen",
                "A. Hormones"));

        questionList.add(new Question("10. Which of the following is the waste product of respiration?",
                "A. ATP",
                "B. H2O",
                "C. CO2",
                "D. all of the above",
                "D. all of the above"));

        questionList.add(new Question("11. Water enter sieve elements by",
                "A. Diffusion",
                "B. Osmosis",
                "C. Active transport",
                "D. all of the above",
                "B. Osmosis"));

        questionList.add(new Question("12. An abnormal replication of genes is called",
                "A. Slicing",
                "B. Mutation",
                "C. Breakage",
                "D. Crossing over",
                "B. Mutation"));

        questionList.add(new Question("13. The stage where chromosomes are located at the center is",
                "A. Interphase",
                "B. Metaphase",
                "C. Anaphase",
                "D. Prophase",
                "B. Metaphase"));

        questionList.add(new Question("14. When ADP is phosphorylated, it produces",
                "A. ATP",
                "B. NAD",
                "C. FADH2",
                "D. NADP",
                "A. ATP"));

        questionList.add(new Question("15. Translocation occurs in the",
                "A. Phloem",
                "B. Xylem",
                "C. stomata",
                "D. lenticel",
                "A. Phloem"));

        questionList.add(new Question("16. Which of these is not a source of sugar?",
                "A. Flower",
                "B. some roots",
                "C. leaf",
                "D. none of the options",
                "C. leaf"));

        questionList.add(new Question("17. The movement of materials in an organism is",
                "A. Transportation",
                "B. Translocation",
                "C. leaf",
                "D. none of the options",
                "B. Translocation"));

        questionList.add(new Question("18. NAD+ is reduced to ____",
                "A. NAD",
                "B. NADH2",
                "C. (NADH)2",
                "D. NADH",
                "D. NADH"));

        questionList.add(new Question("19. PH is used to determine the degree of",
                "A. Neutrality",
                "B. OH-",
                "C. H+ and OH-",
                "D. Alkalinity only",
                "C. H+ and OH-"));

        questionList.add(new Question("20. The phloem comprise the following except",
                "A. Tracheid",
                "B. Schlereid",
                "C. Companion cell",
                "D. Seive tube",
                "A. Tracheid"));

        questionList.add(new Question("21. The absorption of water by the root hairs of plant from the soil is through",
                "A. Osmosis",
                "B. Diffusion",
                "C. All of the above",
                "D. Active transport",
                "A. Osmosis"));

        questionList.add(new Question("22. Plant Nutrient are absorbed from the soil by the",
                "A. Root",
                "B. Root cells",
                "C. Root hairs",
                "D. All are correct",
                "C. Root hairs"));

        questionList.add(new Question("23. At the end of glycolysis _____ FAD is used",
                "A. 2",
                "B. 3",
                "C. 1",
                "D. 0",
                "D. 0"));

        questionList.add(new Question("24. Food is transported to which part of a plant",
                "A. Leaves branches",
                "B. New leaves or branches",
                "C. Old leaves or branches",
                "D. None",
                "B. New leaves or branches"));

        questionList.add(new Question("25. Which one is correct",
                "A. Xylem contains dead cells",
                "B. Xylem contains living cells",
                "C. Phloem contains dead cells",
                "D.  None",
                "A. Xylem contains dead cells"));

        questionList.add(new Question("26. How many ways do plants excrete",
                "A. 2",
                "B. 3",
                "C. 4",
                "D. 1",
                "A. 2"));

        questionList.add(new Question("27. All the following have a double membrane bound except",
                "A. Mitochondria",
                "B. cytoplasm",
                "C. Nucleus",
                "D. Endoplasmic reticulum",
                "D. Endoplasmic reticulum"));

        questionList.add(new Question("28. The solvent property of water is due to the ______ arrangement of its hydrogen and oxygen atom",
                "A. Spatial",
                "B. Asymmetrical",
                "C. linear",
                "D. All of above",
                "B. Asymmetrical"));

        questionList.add(new Question("29. The cell theory was first postulated in",
                "A. 1665",
                "B. 1838",
                "C. 1839",
                "D. 1855",
                "C. 1839"));

        questionList.add(new Question("30. Which of the following macromolecules is constantly found in the nucleus of a cell",
                "A. RNA",
                "B. DNA",
                "C. Histones & RNA",
                "D. Proteins",
                "B. DNA"));

        questionList.add(new Question("31. Which of the following is odd out with respect to enzyme reaction catalyzed?",
                "A. peroxidases",
                "B. kinase",
                "C. dehydrogenases",
                "D. Oxidases",
                "B. kinase"));

        questionList.add(new Question("32. Which of the following processes produces more ATP?",
                "A. Photosynthesis",
                "B. nutrition",
                "C. both of the above",
                "D. cellular respiration",
                "A. Photosynthesis"));

        questionList.add(new Question("33. _______ is the grand substance of the cell",
                "A. Nucleus",
                "B. mitochondria",
                "C. cytoplasm",
                "D. cell wall",
                "A. Nucleus"));

        questionList.add(new Question("34. In animals, anaerobic respiration gives rise to _____?",
                "A. Ethanol",
                "B. lactol",
                "C. alcohol",
                "D. lactic acid",
                "A. Nucleus"));

        questionList.add(new Question("35. In the presence of O2, acetyl coA combines with the oxaloacetic acid to give _____",
                "A. Citric acid",
                "B. oxaloglutaric acid",
                "C. pyruvate",
                "D. succinate",
                "A. Citric acid"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. Which of the following is majorly responsible for phosphorylation during glycolysis",
                "A. Mutase",
                "B. Carbonase",
                "C. Kinase",
                "D. Aldolase",
                "C. Kinase"));

        questionList.add(new Question("2. Which of the following is true",
                "A. Phloem tissue is made up of dead cells",
                "B. Xylem tissue is made up of dead cells",
                "C. Xylem tissue is made up of living cells",
                "D. None of the above",
                "B. Xylem tissue is made up of dead cells"));

        questionList.add(new Question("3. The law of independent assortment of Gene is based on",
                "A. Genes on the same allele",
                "B. Genes on different pairs of homologous chromosome",
                "C. Genes on the same chromosome",
                "D. Genes of same homologous chromosome",
                "B. Genes on different pairs of homologous chromosome"));

        questionList.add(new Question("4. Carboxylation does not occur in _____",
                "A. ETC",
                "B. None is correct",
                "C. Kreb'scycle",
                "D. EMP",
                "D. EMP"));

        questionList.add(new Question("5. Which of these is not part of a chromosome?",
                "A. Arm",
                "B. constriction",
                "C. Metaphase",
                "D. Centromere",
                "C. Metaphase"));

        questionList.add(new Question("6. The long phase of meiosis?",
                "A. S",
                "B. M",
                "C. G1",
                "D. Prophase",
                "D. Prophase"));

        questionList.add(new Question("7. The term for the movement of water vapour from the aerial parts of plant is_____",
                "A. Evaporation",
                "B. Transpiration",
                "C. Translocation",
                "D. Transportation",
                "B. Transpiration"));

        questionList.add(new Question("8. A solution that maintains a constant pH is probably a/an ",
                "A. Acid",
                "B. Buffer",
                "C. Alkaline",
                "D. Standard",
                "B. Buffer"));

        questionList.add(new Question("9. Lactic acid fermentation occurs in the absence of ",
                "A. O2",
                "B. Pyruvate",
                "C. NADH2",
                "D. None of the above",
                "A. O2"));

        questionList.add(new Question("10. Absorption of water by root hairs from the soil is a process of ",
                "A. Transpiration",
                "B. Active transport",
                "C. Osmosis",
                "D. Diffusion",
                "C. Osmosis"));

        questionList.add(new Question("11. Movement of products of photosynthesis is carried out by",
                "A. Xylem",
                "B. Vascular bundles",
                "C. Phloem",
                "D. Stems",
                "C. Phloem"));

        questionList.add(new Question("12. Which is not a type of chromosome?",
                "A. Metacentric",
                "B. Telometric",
                "C. Acrocentric",
                "D. Hollowcentric",
                "D. Hollowcentric"));

        questionList.add(new Question("13. Genetic variation occurs in which of the following",
                "A. Mitosis",
                "B. Meiosis",
                "C. Cytoplasm",
                "D. Nucleus",
                "B. Meiosis"));

        questionList.add(new Question("14. Histones are associated with ",
                "A. Eukaryotic cell",
                "B. Prokaryotic cell",
                "C. Algae",
                "D. None of the options",
                "A. Eukaryotic cell"));

        questionList.add(new Question("15. Mitochondria are numerous in",
                "A. Bacteria",
                "B. Algae",
                "C. None of the options",
                "D. Mammals",
                "D. Mammals"));

        questionList.add(new Question("16. G2 is the .......... phase ",
                "A. Pre synthesis",
                "B. Synthesis",
                "C. Post synthesis",
                "D. None of the options",
                "C. Post synthesis"));

        questionList.add(new Question("17. Glyceraldehyde 3 phosphate and .............. are catalysed by triose" +
                " phosphate isomerase",
                "A. Dihydroxy acetone phosphate",
                "B. Fructose 6 phosphate",
                "C. Dihydroxy 1 3 phosphate",
                "D. None of the options",
                "A. Dihydroxy acetone phosphate"));

        questionList.add(new Question("18. In anaerobic respiration, CO2 is produced in",
                "A. Plants only",
                "B. Plants and animals",
                "C. Animals only",
                "D. None of the option",
                "A. Plants only"));

        questionList.add(new Question("19. All these are processes occurring in dark reaction except",
                "A. Oxygenation",
                "B. Carboxylation",
                "C. Reduction",
                "D. Phosphorylation",
                "D. Phosphorylation"));

        questionList.add(new Question("20. The conversion of ATP and NADPH to ADP, Pi and NAD occurs in",
                "A. Cyclic phosphorylation",
                "B. Dark reaction",
                "C. Non cyclic phosphorylation",
                "D. Reduction",
                "B. Dark reaction"));

        questionList.add(new Question("21. Sugar sink is",
                "A. place where sugar is produced",
                "B. A place where sugar is stored.",
                "C. A place where sugar is carried",
                "D. None of the following",
                "B. A place where sugar is stored."));

        questionList.add(new Question("22. Calcium and magnesium pectate is found in what organelle.",
                "A. Plastic",
                "B. Chloroplasts",
                "C. Vesicles",
                "D. Middle lamella",
                "D. Middle lamella"));

        questionList.add(new Question("23. The following are biological variation that can be passed from parent to offspring except",
                "A. Leaf",
                "B. Germline",
                "C. Root",
                "D. Skin",
                "C. Root"));

        questionList.add(new Question("24. Which of these is not part of a chromosome?",
                "A. Arm",
                "B. constriction",
                "C. Metaphase",
                "D. Anaphase",
                "C. Metaphase"));

        questionList.add(new Question("25. Aerobic respiration occurs in the cytoplasm of______",
                "A. Animals",
                "B. Plants",
                "C. Prokaryotes",
                "D. None of the above",
                "C. Metaphase"));

        questionList.add(new Question("26. The lesser the value of the pH is above 7 the greater its____ ",
                "A. Alkalinity",
                "B. Acidity",
                "C. Neutrality",
                "D. Hydrogen ion concentration",
                "B. Acidity"));

        questionList.add(new Question("27. The O2 released during photosynthesis is from",
                "A. H2O ",
                "B. CO2 ",
                "C. Chlorophyll",
                "D. C6H12O6",
                "A. H2O "));

        questionList.add(new Question("28. A chromosome attached to another chromosome is called",
                "A. Satellite chromosome  ",
                "B. Metacentric ",
                "C. Acrocentric",
                "D. Acrocentric",
                "A. Satellite chromosome  "));

        questionList.add(new Question("29. The solvent property of water is due to the______ arrangement of its hydrogen and oxygen atom",
                "A. Spatial",
                "B. Asymmetrical",
                "C. Linear ",
                "D. All of above",
                "B. Asymmetrical"));

        questionList.add(new Question("30. The isomer of Glyceraldehyde -3- phosphate is ",
                "A. Dihydroxyacetone phosphate",
                "B. Dihydroxy- 3 - phosphate ",
                "C. Dihydroxyacetate phosphate ",
                "D. Glyceraldehyde-1-phosphate",
                "A. Dihydroxyacetone phosphate"));

        questionList.add(new Question("31. More ATP are formed in the ______ in respiration",
                "A. Mitochondria",
                "B. cytoplasm",
                "C. nucleus",
                "D. chloroplast",
                "A. Mitochondria"));

        questionList.add(new Question("32. All the following are component of a cell membrane except ____",
                "A. Protein",
                "B. Carbohydrate",
                "C. Lipid",
                "D. None of the above",
                "D. None of the above"));

        questionList.add(new Question("33. Many enzymes lack catalytic activities in the absence of ___",
                "A. Co-protein",
                "B. Co- factor",
                "C. Co- enzyme",
                "D. Metal ion",
                "B. Co- factor"));

        questionList.add(new Question("34. Enzymes cannot be inhibited by _____",
                "A. High temperature",
                "B. High pH",
                "C. Heavy metal",
                "D. Optimum temperature",
                "D. Optimum temperature"));

        questionList.add(new Question("35. Which of the following is not a property of water?",
                "A. Solvent property",
                "B. Thermal property",
                "C. Surface tension",
                "D. Liquid property",
                "B. Thermal property"));

    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question("1. The process of recombination of genetic materials " +
                "during meiotic cell division is called___",
                "A. Synapse",
                "B. Chiasma",
                "C. Crossing over",
                "D. Transcription",
                "C. Crossing over"));

        questionList.add(new Question("2. Which of the following is odd one out?",
                "A. Fructose + glucose = sucrose",
                "B. Fructose + fructose = maltose",
                "C. Glucose + galactose = lactose",
                "D. Glucose + glucose = maltose",
                "B. Fructose + fructose = maltose"));

        questionList.add(new Question("3. Lysosome is also known as ___ box",
                "A. Protective",
                "B. Suicidal",
                "C. Adaptive",
                "D. None of the above",
                "B. Suicidal"));

        questionList.add(new Question("4. Which of the following is not a protein?",
                "A. Keratin",
                "B. Haemoglobin",
                "C. Stearin",
                "D. Myoglobin",
                "C. Stearin"));

        questionList.add(new Question("5. The final product of the Calvin cycle is ____",
                "A. RUDP",
                "B. PGA",
                "C. ATP",
                "D. PGAL",
                "D. PGAL"));

        questionList.add(new Question("6. The light reaction stage of photosynthesis takes place " +
                "in the membranes of small sacs called__",
                "A. Chloroplast",
                "B. grana",
                "C. thylakoids",
                "D. photosystems",
                "C. thylakoids"));

        questionList.add(new Question("7. Colours of light most useful in photosynthesis are ___",
                "A. green, yellow and orange",
                "B. red, violet and blue",
                "C. infrared, red and yellow",
                "D. red, white and blue",
                "B. red, violet and blue"));

        questionList.add(new Question("8. During what stage of photosynthesis is O2 produced?",
                "A. Cyclic photophosphorylation",
                "B. the light-dependent reactions involving photosystems i and ii",
                "C. carbon fixation",
                "D. O2 is not produced during photosynthesis",
                "B. the light-dependent reactions involving photosystems i and ii"));

        questionList.add(new Question("9. The pigment molecules responsible for photosynthesis" +
                " are located in the _____",
                "A. Cytoplasm of the cell",
                "B. stroma of the chloroplast",
                "C. Thylakoid membrane of the chloroplast",
                "D. all of the above",
                "C. Thylakoid membrane of the chloroplast"));

        questionList.add(new Question("10. Both carotenoids and chlorophylls ____",
                "A. Are pigments",
                "B. absorb photons of all energy ranges",
                "C. none of the above",
                "D. all of the above",
                "A. Are pigments"));

        questionList.add(new Question("11. Which of the following is the correct sequence for the movement of electrons during the" +
                " light-dependent reactions of plant?",
                "A. P680  à P700  à H2O à NaDP+",
                "B. H2O àP700  à NaDP+ à P680",
                "C. P680  à H2O à P700  à NaDP+",
                "D. H2O à P680  à P700  à NaDP+",
                "D. H2O à P680  à P700  à NaDP+"));

        questionList.add(new Question("12. Which of the following must pyruvic acid be converted into before the" +
                " Krebs cycle can proceed?",
                "A. NADH",
                "B. glucose",
                "C. citric acid",
                "D. acetyl-coA",
                "D. acetyl-coA"));

        questionList.add(new Question("13. Which of the following occurs in lactic acid fermentation?",
                "A. oxygen is consumed",
                "B. lactic acid is converted into pyruvic acid",
                "C. NAD+  is regenerated for use in glycolysis",
                "D. electrons pass through the electron transport chain",
                "C. NAD+  is regenerated for use in glycolysis"));

        questionList.add(new Question("14. Which of the following is not a product of Krebs cycle?",
                "A. CO2",
                "B. ATP",
                "C. FADH2",
                "D. ethyl alcohol",
                "D. ethyl alcohol"));

        questionList.add(new Question("15. 5 carbon compound  ----> 4 carbon compound + NADH + H+ + ATP " +
                "This reaction occurs during which of the following processes?",
                "A. Krebs cycle",
                "B. acetyl-coA formation",
                "C. alcoholic fermentation",
                "D. lactic acid fermentation",
                "A. Krebs cycle"));

        questionList.add(new Question("16. Glucose→ glycolysis(i)→  pyruvic acid(ii)→acetyl-coA(iii)→Krebs cycle(iv). " +
                "At which of the points is ATP, the main energy currency of the cell, produced?",
                "A. i only",
                "B. ii only",
                "C. i and iv",
                "D. i,ii,iii and iv",
                "C. i and iv"));

        questionList.add(new Question("17. During what stage of photosynthesis are ATP and NADPH converted" +
                " to ADP + Pi and NADP+?",
                "A. Light-dependent reactions",
                "B. light-independent reactions",
                "C. both of the above",
                "D. none of the above",
                "B. light-independent reactions"));

        questionList.add(new Question("18. H2O vapour exits and CO2 enters a leaf through the",
                "A. Stromata",
                "B. stomata",
                "C. grana",
                "D. leaf",
                "B. stomata"));

        questionList.add(new Question("19. What energy-rich organic compound is produced as a result" +
                " of the Calvin cycle?",
                "A. ATP",
                "B. H2O",
                "C. NADPH",
                "D. C6H12O6",
                "D. C6H12O6"));

        questionList.add(new Question("20. High energy photons ____",
                "A. Long wavelength",
                "B. have short wavelength",
                "C. cannot be absorbed",
                "D. none of the above",
                "B. have short wavelength"));

        questionList.add(new Question("21. During photosynthesis, photons raise electrons to higher energy levels." +
                " These excited electrons belong to what compound?",
                "A. Chlorophyll",
                "B. ATP",
                "C. photosystem",
                "D. electron acceptor",
                "A. Chlorophyll"));

        questionList.add(new Question("22. Which of the following about photosynthesis was matched correctly?",
                "A. Barnes (1898),Calvin(1940)",
                "B. Barnes(1940),Calvin(1898)",
                "C. Calvin(1940),Barnes(1889)",
                "D. Calvin(1898),Barnes(1904)",
                "A. Barnes (1898),Calvin(1940)"));

        questionList.add(new Question("23. Which of the following process is common to both the light" +
                " and dark reactions?",
                "A. carboxylation",
                "B. reduction",
                "C. phosphorylation",
                "D. isomerization",
                "C. phosphorylation"));

        questionList.add(new Question("24. PGAL is the starting material for the synthesis of _____",
                "A. Sucrose",
                "B. maltose",
                "C. lactose",
                "D. galactose",
                "A. Sucrose"));

        questionList.add(new Question("25. Which of the following statement is correct about H2O?",
                "A. The cell is made up of 80% of H2O",
                "B. The brain is made up of 78% of H2O",
                "C. both of the above",
                "D. none of the above",
                "C. both of the above"));

        questionList.add(new Question("26. H2O effectively weakens the attraction between the ions of NaCl because ____",
                "A. Presence of negative charge",
                "B. presence of positive charge",
                "C. presence of positive and negative charges",
                "D. none of the above",
                "C. presence of positive and negative charges"));

        questionList.add(new Question("27. The following properties emanate from the polarity of H2O except ___",
                "A. Solvent property",
                "B. hydrogen bond",
                "C. freezing property",
                "D. surface tension",
                "C. freezing property"));

        questionList.add(new Question("28. H2O has a very high heat capacity because ___",
                "A. A large increase in heat results in a comparatively high rise in temperature",
                "B. a small increase in heat results in a comparatively small rise in temperature",
                "C. a large increase in heat results in a comparatively small rise in temperature",
                "D. none of the above",
                "C. a large increase in heat results in a comparatively small rise in temperature"));

        questionList.add(new Question("29. Surface tension in H2O is caused by ____ ",
                "A. Outward acting cohesive force",
                "B. inward acting cohesive force",
                "C. freezing property",
                "D. surface tension",
                "C. freezing property"));

        questionList.add(new Question("30. Which of the following properties of H2O is important in" +
                " the development of plasma membrane?",
                "A. Solvent property",
                "B. freezing property",
                "C. freezing property",
                "D. surface tension",
                "C. freezing property"));

        questionList.add(new Question("31. The following do not resist changes in pH on dilution except _______",
                "A. NaHCO3",
                "B. Na+",
                "C. HCO-3",
                "D. none of the above",
                "A. NaHCO3"));

        questionList.add(new Question("32. Which of the following correctly define the pH?",
                "A. Log [1/H3O+]",
                "B. log [1/OH-]",
                "C. log [1/H2O]",
                "D. log [1/SO42-]",
                "B. log [1/OH-]"));

        questionList.add(new Question("33. A compound with pH of 6.9 is said to be ____",
                "A. Slightly acidic",
                "B. slightly basic",
                "C. highly acidic",
                "D. highly basic",
                "A. Slightly acidic"));

        questionList.add(new Question("34. Most liquid exclusive of H2O _____",
                "A. Decrease in density with increase in temperature",
                "B. increase in density with increase in temperature",
                "C. decrease in density with decrease in temperature",
                "D. none of the above",
                "A. Decrease in density with increase in temperature"));

        questionList.add(new Question("35. Which of the following statement is correct?",
                "A. an enzyme devoid of co-factors is slightly active",
                "B. an enzyme devoid of co-factors is not active",
                "C. an enzyme devoid of co-factors is very active",
                "D. none of the above",
                "B. an enzyme devoid of co-factors is not active"));
    }
}