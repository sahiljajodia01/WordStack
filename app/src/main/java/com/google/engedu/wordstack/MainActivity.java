/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordstack;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 5;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    private String word1, word2;
    private Stack<LetterTile> placedTiles = new Stack<>();
    private LinearLayout word1LinearLayout;
    private LinearLayout word2LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word1LinearLayout = (LinearLayout) findViewById(R.id.word1);
        word2LinearLayout = (LinearLayout) findViewById(R.id.word2);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                if(word.length() >= WORD_LENGTH) {
                    words.add(word);
                }
                /**
                 **
                 **  YOUR CODE GOES HERE
                 **
                 **/
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        verticalLayout.addView(stackedLayout, 3);

        View word1LinearLayout = findViewById(R.id.word1);
//        word1LinearLayout.setOnTouchListener(new TouchListener());
        word1LinearLayout.setOnDragListener(new DragListener());
        View word2LinearLayout = findViewById(R.id.word2);
//        word2LinearLayout.setOnTouchListener(new TouchListener());
        word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                placedTiles.push(tile);
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }
                /**
                 **
                 **  YOUR CODE GOES HERE
                 **
                 **/
                return true;
            }
            return false;
        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }
                    /**
                     **
                     **  YOUR CODE GOES HERE
                     **
                     **/
                    placedTiles.push(tile);
                    return true;
            }
            return false;
        }
    }

    public boolean onStartGame(View view) {
        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText("Game started");

        stackedLayout.clear();
        word1LinearLayout.removeAllViews();
        word2LinearLayout.removeAllViews();
        Random random = new Random();
        int index1 = random.nextInt(words.size());
        int index2 = random.nextInt(words.size());


        word1 = words.get(index1);
        word2 = words.get(index2);

        Log.i("word1", word1);
        Log.i("word2", word2);
//        String word = word1 + word2;
//        List<String> wordList = Arrays.asList(word.split(""));
//        Collections.shuffle(wordList);
        String scrambledWord = "";
        int m = 0,n = 0;

//        if(word1.length() > word2.length()) {
//            for (int index = 0; index < word2.length(); index++) {
//                int randomIndex = random.nextInt(1);
//                if(randomIndex == 0) {
//                    scrambledWord += word1.charAt(index);
//                }
//                else {
//                    scrambledWord += word2.charAt(index);
//                }
//            }
//            for(int index = word2.length(); index< word1.length(); index++) {
//                scrambledWord += word1.charAt(index);
//            }
//        }
//        else {
//            for (int index = 0; index < word1.length(); index++) {
//                int randomIndex = random.nextInt(1);
//                if(randomIndex == 0) {
//                    scrambledWord += word1.charAt(index);
//                }
//                else {
//                    scrambledWord += word2.charAt(index);
//                }
//            }
//            for(int index =word1.length(); index< word2.length(); index++) {
//                scrambledWord += word2.charAt(index);
//            }
//        }

        while(m < word1.length() && n < word2.length()) {
            int randomIndex = random.nextInt(2);

            if(randomIndex == 0) {
                scrambledWord += word1.charAt(m);
                m++;
            }
            else {
                scrambledWord += word2.charAt(n);
                n++;
            }
        }
        Log.i("m", Integer.toString(m));
        Log.i("n", Integer.toString(n));

        if(m == word1.length()) {
            for(int i=n; i<word2.length(); i++) {
                scrambledWord += word2.charAt(i);
            }
        }
        else if(n == word2.length()) {
            for(int i=m; i<word1.length(); i++) {
                scrambledWord += word1.charAt(i);
            }
        }

        messageBox.setText(scrambledWord);
        for(int i=scrambledWord.length() - 1; i >=0; i--) {
            stackedLayout.push(new LetterTile(this, scrambledWord.charAt(i)));
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        return true;
    }

    public boolean onUndo(View view) {
        if(!placedTiles.empty()) {
            LetterTile popped = placedTiles.pop();
            popped.moveToViewGroup(stackedLayout);
        }

        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        return true;
    }
}
