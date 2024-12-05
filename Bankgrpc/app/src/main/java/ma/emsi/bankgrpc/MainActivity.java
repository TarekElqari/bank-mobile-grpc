package ma.emsi.bankgrpc;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ma.emsi.bankgrpc.activities.AccountDetailsActivity;
import ma.emsi.bankgrpc.activities.AddAccountActivity;
import ma.emsi.bankgrpc.activities.AllAccountsActivity;
import ma.emsi.bankgrpc.activities.StatisticsActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set padding for system bars (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnAllAccounts).setOnClickListener(v -> {
            startActivity(new Intent(this, AllAccountsActivity.class));
        });

        findViewById(R.id.btnAddAccount).setOnClickListener(v -> {
            startActivity(new Intent(this, AddAccountActivity.class));
        });

        findViewById(R.id.btnAccountDetails).setOnClickListener(v -> {
            startActivity(new Intent(this, AccountDetailsActivity.class));
        });

        findViewById(R.id.btnStatistics).setOnClickListener(v -> {
            startActivity(new Intent(this, StatisticsActivity.class));
        });
    }
}
