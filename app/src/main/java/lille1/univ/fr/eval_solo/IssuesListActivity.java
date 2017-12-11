package lille1.univ.fr.eval_solo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import lille1.univ.fr.eval_solo.adapter.RecyclerAdapter;
import lille1.univ.fr.eval_solo.model.Issue;

public class IssuesListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_list);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddIssueActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = findViewById(R.id.recycler_view_issues);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Resume", "OK");
        mAdapter = new RecyclerAdapter(Issue.listAll(Issue.class), IssuesListActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issues_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(this.mRecyclerView, "Base de donnée réinitialisée", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            mockData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mockData() {
        Issue.deleteAll(Issue.class);
        new Issue("Arbre à tailler", "description de mon problème").save();
        new Issue("Arbre à tailler", "description de mon problème").save();
        new Issue("Arbre à abattre", "description de mon problème").save();
        new Issue("Détritus", "à ramasser").save();
        new Issue("Arbre à tailler", "description de mon problème").save();
        new Issue("Haie à tailler", "description de mon problème").save();
        new Issue("Mauvaise herbe", "description de mon problème").save();
    }
}
