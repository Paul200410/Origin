package jp.ac.gifu_u.hosokawa.myapplication;

import java.util.ArrayList;

public class Stroke {

    // 各点のx座標とy座標
    public ArrayList<Float> xList = new ArrayList<>();
    public ArrayList<Float> yList = new ArrayList<>();

    // この線の色（Color.REDなど）
    public int color;

    // コンストラクタで色を受け取って記録
    public Stroke(int color) {
        this.color = color;
    }

    // 点を追加するメソッド
    public void addPoint(Float x, Float y) {
        xList.add(x);
        yList.add(y);
    }
}
