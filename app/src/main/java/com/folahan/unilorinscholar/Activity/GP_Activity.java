package com.folahan.unilorinscholar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.folahan.unilorinscholar.databinding.ActivityGpBinding;


public class GP_Activity extends AppCompatActivity {
    private ActivityGpBinding gpBinding;
    private int score1, cUnit1, cUnit2, score2,cUnit3, score3, cUnit4, score4, cUnit5, score5, cUnit6, score6;
    private int i = 0;
    private int cUnit7, score7, cUnit8, score8, cUnit9, score9, cUnit10, score10, cUnit11, score11, cUnit12, score12;
    private int point1=0, point2=0, point3=0, point4=0, point5=0, point6=0, point7=0, point8=0, point9=0, point10=0, point11=0, point12=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpBinding = ActivityGpBinding.inflate(getLayoutInflater());
        setContentView(gpBinding.getRoot());
        gpBinding.btnClick.setOnClickListener(view -> {
            calculatePointsObtained();
            calculateObtainablePoints();
        });

        gpBinding.btnAdd.setOnClickListener(task -> {
            if (gpBinding.tableRow8.getVisibility() == View.GONE) {
                i++;
                gpBinding.tableRow8.setVisibility(View.VISIBLE);
            } else if (gpBinding.tableRow8.getVisibility() == View.VISIBLE) {
                i++;
                gpBinding.tableRow9.setVisibility(View.VISIBLE);
            } else if (gpBinding.tableRow9.getVisibility() == View.VISIBLE) {
                i++;
                gpBinding.tableRow10.setVisibility(View.VISIBLE);
            } else if (gpBinding.tableRow10.getVisibility() == View.VISIBLE && i==4) {
                i++;
                gpBinding.tableRow11.setVisibility(View.VISIBLE);
            } else if (gpBinding.tableRow11.getVisibility() == View.VISIBLE && i==4) {
                i++;
                gpBinding.tableRow12.setVisibility(View.VISIBLE);
            } else if (gpBinding.tableRow12.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, "Bandwidth Limit Reached", Toast.LENGTH_SHORT).show();
            }
        });

        gpBinding.btnRemove.setOnClickListener(task -> {
            if (gpBinding.tableRow8.getVisibility() == View.GONE) {
                Toast.makeText(this, "Least Bandwidth reached", Toast.LENGTH_SHORT).show();
            }
            gpBinding.tableRow8.setVisibility(View.GONE);
            if (gpBinding.tableRow8.getVisibility() == View.GONE) {
                gpBinding.tableRow9.setVisibility(View.GONE);
            } else if (gpBinding.tableRow9.getVisibility() == View.GONE) {
                gpBinding.tableRow10.setVisibility(View.GONE);
            } else if (gpBinding.tableRow10.getVisibility() == View.GONE) {
                gpBinding.tableRow11.setVisibility(View.GONE);
            } else if (gpBinding.tableRow11.getVisibility() == View.GONE) {
                gpBinding.tableRow12.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Bandwidth Limit Reached", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculatePointsObtained() {
        score1 = Integer.parseInt(gpBinding.score1.getText().toString());
        cUnit1 = Integer.parseInt(gpBinding.cUnit1.getText().toString());
        score2 = Integer.parseInt(gpBinding.score2.getText().toString());
        cUnit2 = Integer.parseInt(gpBinding.cUnit2.getText().toString());
        score3 = Integer.parseInt(gpBinding.score3.getText().toString());
        cUnit3 = Integer.parseInt(gpBinding.cUnit3.getText().toString());
        score4 = Integer.parseInt(gpBinding.score4.getText().toString());
        cUnit4 = Integer.parseInt(gpBinding.cUnit4.getText().toString());
        score5 = Integer.parseInt(gpBinding.score5.getText().toString());
        cUnit5 = Integer.parseInt(gpBinding.cUnit5.getText().toString());
        score6 = Integer.parseInt(gpBinding.score6.getText().toString());
        cUnit6 = Integer.parseInt(gpBinding.cUnit6.getText().toString());
        score7 = Integer.parseInt(gpBinding.score7.getText().toString());
        cUnit7 = Integer.parseInt(gpBinding.cUnit7.getText().toString());
        score8 = Integer.parseInt(gpBinding.score8.getText().toString());
        cUnit8 = Integer.parseInt(gpBinding.cUnit8.getText().toString());
        score9 = Integer.parseInt(gpBinding.score9.getText().toString());
        cUnit9 = Integer.parseInt(gpBinding.cUnit9.getText().toString());
        score10 = Integer.parseInt(gpBinding.score10.getText().toString());
        cUnit10 = Integer.parseInt(gpBinding.cUnit10.getText().toString());
        score11 = Integer.parseInt(gpBinding.score11.getText().toString());
        cUnit11 = Integer.parseInt(gpBinding.cUnit11.getText().toString());
        score12 = Integer.parseInt(gpBinding.score12.getText().toString());
        cUnit12 = Integer.parseInt(gpBinding.cUnit12.getText().toString());

        if (score1 > 69) {
            point1 = cUnit1*5;
        } else if (score1 > 59) {
            point1 = cUnit1*4;
        } else if (score1 > 49) {
            point1 = cUnit1*3;
        } else if (score1 > 45) {
            point1 = cUnit1 * 2;
        } else if(score1 > 39) {
            point1 = cUnit1;
        } else {
            point1 = 0;
        }

        if (score2 > 69) {
            point2 = cUnit2*5;
        } else if (score1 > 59) {
            point2 = cUnit2*4;
        } else if (score2 > 49) {
            point2 = cUnit2*3;
        } else if (score2 > 45) {
            point2 = cUnit2 * 2;
        } else if(score2 > 39) {
            point2 = cUnit2;
        } else {
            point2 = 0;
        }

        if (score3 > 69) {
            point3 = cUnit3*5;
        } else if (score3 > 59) {
            point3 = cUnit3*4;
        } else if (score3 > 49) {
            point3 = cUnit3*3;
        } else if (score3 > 45) {
            point3 = cUnit3 * 2;
        } else if(score3 > 39) {
            point3 = cUnit3;
        } else {
            point3 = 0;
        }

        if (score4 > 69) {
            point4 = cUnit4*5;
        } else if (score4 > 59) {
            point4 = cUnit4*4;
        } else if (score4 > 49) {
            point4 = cUnit4*3;
        } else if (score4 > 45) {
            point4 = cUnit4 * 2;
        } else if(score4 > 39) {
            point4 = cUnit4;
        } else {
            point4 = 0;
        }

        if (score5 > 69) {
            point5 = cUnit5*5;
        } else if (score5 > 59) {
            point5 = cUnit5*4;
        } else if (score5 > 49) {
            point5 = cUnit5*3;
        } else if (score5 > 45) {
            point5 = cUnit5 * 2;
        } else if(score5 > 39) {
            point5 = cUnit5;
        } else {
            point5 = 0;
        }

        if (score6 > 69) {
            point6 = cUnit6*5;
        } else if (score6 > 59) {
            point6 = cUnit6*4;
        } else if (score6 > 49) {
            point6 = cUnit6*3;
        } else if (score6 > 45) {
            point6 = cUnit6 * 2;
        } else if(score6 > 39) {
            point6 = cUnit6;
        } else {
            point6 = 0;
        }

        if (score7 > 69) {
            point7 = cUnit7*5;
        } else if (score7 > 59) {
            point7 = cUnit7*4;
        } else if (score7 > 49) {
            point7 = cUnit7*3;
        } else if (score7 > 45) {
            point7 = cUnit7 * 2;
        } else if(score7 > 39) {
            point7 = cUnit7;
        } else {
            point7 = 0;
        }

        if (score8 > 69) {
            point8 = cUnit8*5;
        } else if (score8 > 59) {
            point8 = cUnit8*4;
        } else if (score8 > 49) {
            point8 = cUnit8*3;
        } else if (score8 > 45) {
            point8 = cUnit8 * 2;
        } else if(score8 > 39) {
            point8 = cUnit8;
        } else {
            point8 = 0;
        }



        if (score9 > 69) {
            point9 = cUnit9*5;
        } else if (score9 > 59) {
            point9 = cUnit9*4;
        } else if (score9 > 49) {
            point9 = cUnit9*3;
        } else if (score9 > 45) {
            point9 = cUnit9 * 2;
        } else if(score9 > 39) {
            point9 = cUnit9;
        } else {
            point9 = 0;
        }

        if (score10 > 69) {
            point10 = cUnit10*5;
        } else if (score10 > 59) {
            point10 = cUnit10*4;
        } else if (score10 > 49) {
            point10 = cUnit10*3;
        } else if (score10 > 45) {
            point10 = cUnit10 * 2;
        } else if(score10 > 39) {
            point10 = cUnit10;
        } else {
            point10 = 0;
        }

        if (score11 > 69) {
            point11 = cUnit11*5;
        } else if (score10 > 59) {
            point11 = cUnit10*4;
        } else if (score11 > 49) {
            point11 = cUnit11*3;
        } else if (score11 > 45) {
            point11 = cUnit11 * 2;
        } else if(score11 > 39) {
            point11 = cUnit11;
        } else {
            point11 = 0;
        }

        if (score12 > 69) {
            point12 = cUnit12*5;
        } else if (score12 > 59) {
            point12 = cUnit12*4;
        } else if (score12 > 49) {
            point12 = cUnit12*3;
        } else if (score12 > 45) {
            point12 = cUnit12 * 2;
        } else if(score12 > 39) {
            point12 = cUnit12;
        } else {
            point12 = 0;
        }

        gpBinding.pointsObatained1.setText("Points: "+ point1);
        gpBinding.pointsObatained2.setText("Points: "+ point2);
        gpBinding.pointsObatained3.setText("Points: "+ point3);
        gpBinding.pointsObatained4.setText("Points: "+ point4);
        gpBinding.pointsObatained5.setText("Points: "+ point5);
        gpBinding.pointsObatained6.setText("Points: "+ point6);
        gpBinding.pointsObatained7.setText("Points: "+ point7);
        gpBinding.pointsObatained8.setText("Points: "+ point8);
        gpBinding.pointsObatained9.setText("Points: "+ point9);
        gpBinding.pointsObatained10.setText("Points: "+ point10);
        gpBinding.pointsObatained11.setText("Points: "+ point11);
        gpBinding.pointsObatained12.setText("Points: "+ point12);

    }

    private void calculateObtainablePoints() {
        score1 = Integer.parseInt(gpBinding.score1.getText().toString());
        cUnit1 = Integer.parseInt(gpBinding.cUnit1.getText().toString());
        score2 = Integer.parseInt(gpBinding.score2.getText().toString());
        cUnit2 = Integer.parseInt(gpBinding.cUnit2.getText().toString());
        score3 = Integer.parseInt(gpBinding.score3.getText().toString());
        cUnit3 = Integer.parseInt(gpBinding.cUnit3.getText().toString());
        score4 = Integer.parseInt(gpBinding.score4.getText().toString());
        cUnit4 = Integer.parseInt(gpBinding.cUnit4.getText().toString());
        score5 = Integer.parseInt(gpBinding.score5.getText().toString());
        cUnit5 = Integer.parseInt(gpBinding.cUnit5.getText().toString());
        score6 = Integer.parseInt(gpBinding.score6.getText().toString());
        cUnit6 = Integer.parseInt(gpBinding.cUnit6.getText().toString());
        score7 = Integer.parseInt(gpBinding.score7.getText().toString());
        cUnit7 = Integer.parseInt(gpBinding.cUnit7.getText().toString());
        score8 = Integer.parseInt(gpBinding.score8.getText().toString());
        cUnit8 = Integer.parseInt(gpBinding.cUnit8.getText().toString());
        score9 = Integer.parseInt(gpBinding.score9.getText().toString());
        cUnit9 = Integer.parseInt(gpBinding.cUnit9.getText().toString());
        score10 = Integer.parseInt(gpBinding.score10.getText().toString());
        cUnit10 = Integer.parseInt(gpBinding.cUnit10.getText().toString());
        score11 = Integer.parseInt(gpBinding.score11.getText().toString());
        cUnit11 = Integer.parseInt(gpBinding.cUnit11.getText().toString());
        score12 = Integer.parseInt(gpBinding.score12.getText().toString());
        cUnit12 = Integer.parseInt(gpBinding.cUnit12.getText().toString());

        point1 = cUnit1 * 5;
        point2 = cUnit2 * 5;
        point3 = cUnit3 * 5;
        point4 = cUnit4 * 5;
        point5 = cUnit5 * 5;
        point6 = cUnit6 * 5;
        point7 = cUnit7 * 5;
        point8 = cUnit8 * 5;
        point9 = cUnit9 * 5;
        point10 = cUnit10 * 5;
        point11 = cUnit11 * 5;
        point12 = cUnit12 * 5;

        gpBinding.obtainablePoints1.setText("Points: "+ point1);
        gpBinding.obtainablePoints2.setText("Points: "+ point2);
        gpBinding.obtainablePoints3.setText("Points: "+ point3);
        gpBinding.obtainablePoints4.setText("Points: "+ point4);
        gpBinding.obtainablePoints5.setText("Points: "+ point5);
        gpBinding.obtainablePoints6.setText("Points: "+ point6);
        gpBinding.obtainablePoints7.setText("Points: "+ point7);
        gpBinding.obtainablePoints8.setText("Points: "+ point8);
        gpBinding.obtainablePoints9.setText("Points: "+ point9);
        gpBinding.obtainablePoints10.setText("Points: "+ point10);
        gpBinding.obtainablePoints11.setText("Points: "+ point11);
        gpBinding.obtainablePoints12.setText("Points: "+ point12);
    }

    private void getPoints(int units, int score, int points) {
        if (score > 69) {
            points = units*5;
        } else if (score > 59) {
            points = units*4;
        } else if (score > 49) {
            points = units*3;
        } else if (score > 45) {
            points = units * 2;
        } else if(score > 39) {
            points = units;
        } else {
            points = 0;
        }
    }
}



