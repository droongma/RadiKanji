package edu.skku.cs.radikanji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import edu.skku.cs.radikanji.bookmark.BookmarkFragment;
import edu.skku.cs.radikanji.kanji.KanjiFragment;
import edu.skku.cs.radikanji.radical.RadicalFragment;

public class LearningScreenActivity extends AppCompatActivity {

    KanjiFragment kanjiFragment;
    RadicalFragment radicalFragment;
    BookmarkFragment bookmarkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_screen);
        
        // fragment와 tab을 활용하여, 각 탭을 누르면 적절한 화면을 보여주도록 구성
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Kanji"));
        tabs.addTab(tabs.newTab().setText("Radical"));
        tabs.addTab(tabs.newTab().setText("Bookmark"));

        kanjiFragment = new KanjiFragment();
        radicalFragment = new RadicalFragment();
        bookmarkFragment = new BookmarkFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        
        // 탭에서 선택하여 볼 수 있는 Fragment 모곩
        String[] fragmentNames = {"KanjiFragment", "RadicalFragment", "BookmarkFragment"};
        fragmentManager.beginTransaction().replace(R.id.contentContatiner, kanjiFragment,
                "KanjiFragment").commit();

        // 프래그먼트가 존재하면 숨김을 해제하고 보여줌. 존재하지 않으면 프래그먼트를 매너지에 추가함
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selected = null;
                int position = tab.getPosition();
                String name;
                switch(position) {
                    case 0:
                        name = fragmentNames[0];
                        if(fragmentManager.findFragmentByTag(name) != null){ // 프래그먼트 존재시 보여줌
                            fragmentManager.beginTransaction().show(
                                    fragmentManager.findFragmentByTag(name)).commit();
                        } else { // 프래그먼트가 없으면 매니저에 추가
                            fragmentManager.beginTransaction().add(
                                    R.id.contentContatiner, KanjiFragment.newInstance(), name).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[1]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[1])).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[2]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[2])).commit();
                        }
                        break;
                    case 1:
                        name = fragmentNames[1];
                        if(fragmentManager.findFragmentByTag(name) != null){ // 프래그먼트 존재시 보여줌
                            fragmentManager.beginTransaction().show(
                                    fragmentManager.findFragmentByTag(name)).commit();
                        } else { // 프래그먼트가 없으면 매니저에 추가
                            fragmentManager.beginTransaction().add(
                                    R.id.contentContatiner, radicalFragment.newInstance(), name).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[0]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[0])).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[2]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[2])).commit();
                        }
                        break;
                    case 2:
                        name = fragmentNames[2];
                        if(fragmentManager.findFragmentByTag(name) != null){ // 프래그먼트 존재시 보여줌
                            fragmentManager.beginTransaction().show(
                                    fragmentManager.findFragmentByTag(name)).commit();
                        } else { // 프래그먼트가 없으면 매니저에 추가
                            fragmentManager.beginTransaction().add(
                                    R.id.contentContatiner, BookmarkFragment.newInstance(), name).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[0]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[0])).commit();
                        }
                        if(fragmentManager.findFragmentByTag(fragmentNames[1]) != null){
                            fragmentManager.beginTransaction().hide(
                                    fragmentManager.findFragmentByTag(fragmentNames[1])).commit();
                        }
                        break;
                }
//                fragmentManager.beginTransaction().replace(R.id.contentContatiner,
//                        selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}