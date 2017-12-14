package lille1.univ.fr.eval_solo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lille1.univ.fr.eval_solo.AddIssueActivity;
import lille1.univ.fr.eval_solo.R;
import lille1.univ.fr.eval_solo.model.Issue;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.IssueHolder> {

    private Context ctx;
    private List<Issue> issuesList;

    public RecyclerAdapter( List<Issue> issuesList, Context ctx) {
        this.issuesList = issuesList;
        this.ctx = ctx;
    }

    @Override
    public IssueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_issue_row, parent, false);
        return new IssueHolder(inflatedView, this.ctx);
    }

    @Override
    public void onBindViewHolder(IssueHolder holder, int position) {
        Issue issue = issuesList.get(position);
        holder.loadIssue(issue);
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    public static class IssueHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textViewDescription;
        private View parentView;

        Context ctx;
        public IssueHolder(View view, Context context) {
            super(view);
            this.parentView = view;
            this.ctx = context;
        }

        public void loadIssue(final Issue issue) {
            textView = parentView.findViewById(R.id.line_type);
            textView.setText(issue.getType());
            textViewDescription = parentView.findViewById(R.id.line_description);
            textViewDescription.setText(issue.getDescription());
            parentView.findViewById(R.id.issue_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ctx, AddIssueActivity.class);
                    i.putExtra("issue_id", issue.getId());
                    ctx.startActivity(i);

                }
            });
        }
    }
}
