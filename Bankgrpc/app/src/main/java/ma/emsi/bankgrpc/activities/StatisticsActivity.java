package ma.emsi.bankgrpc.activities;



import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.emsi.bankgrpc.R;
import ma.emsi.bankgrpc.client.CompteServiceClient;
import ma.emsi.bankgrpc.stubs.GetTotalSoldeResponse;
import ma.emsi.bankgrpc.stubs.SoldeStats;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvStatistics;
    private Button btnFetchStats;
    private CompteServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvStatistics = findViewById(R.id.tvStatistics);
        btnFetchStats = findViewById(R.id.btnFetchStats);

        client = new CompteServiceClient("10.0.2.2", 9090);

        btnFetchStats.setOnClickListener(v -> fetchStatistics());
    }

    private void fetchStatistics() {
        new Thread(() -> {
            try {
                GetTotalSoldeResponse response = client.getTotalSolde();
                SoldeStats stats = response.getStats();
                runOnUiThread(() -> tvStatistics.setText("Total Accounts: " + stats.getCount()
                        + "\nSum: " + stats.getSum()
                        + "\nAverage: " + stats.getAverage()));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to fetch statistics", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
