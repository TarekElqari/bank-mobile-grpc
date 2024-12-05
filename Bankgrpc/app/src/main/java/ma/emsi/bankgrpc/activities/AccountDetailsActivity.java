package ma.emsi.bankgrpc.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.emsi.bankgrpc.R;
import ma.emsi.bankgrpc.client.CompteServiceClient;
import ma.emsi.bankgrpc.stubs.Compte;
import ma.emsi.bankgrpc.stubs.GetCompteByIdResponse;

public class AccountDetailsActivity extends AppCompatActivity {

    private EditText etAccountId;
    private Button btnFetchDetails;
    private TextView tvAccountId;
    private TextView tvAccountDetails;
    private CompteServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        etAccountId = findViewById(R.id.etAccountId);
        btnFetchDetails = findViewById(R.id.btnFetchDetails);
        tvAccountId = findViewById(R.id.tvAccountId);
        tvAccountDetails = findViewById(R.id.tvAccountDetails);

        client = new CompteServiceClient("10.0.2.2", 9090); // Update with your server address

        btnFetchDetails.setOnClickListener(v -> fetchAccountDetails());

        // Enable copying the account ID
        tvAccountId.setOnLongClickListener(v -> {
            String accountId = tvAccountId.getText().toString().replace("Account ID: ", "");
            if (!accountId.equals("N/A")) {
                copyToClipboard(accountId);
                Toast.makeText(this, "Account ID copied to clipboard", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void fetchAccountDetails() {
        String accountId = etAccountId.getText().toString();
        if (accountId.isEmpty()) {
            Toast.makeText(this, "Please enter an Account ID", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                GetCompteByIdResponse response = client.getCompteById(accountId);
                Compte compte = response.getCompte();

                runOnUiThread(() -> {
                    tvAccountId.setText("Account ID: " + compte.getId());
                    tvAccountDetails.setText("Solde: $" + compte.getSolde()
                            + "\nType: " + compte.getType()
                            + "\nDate Created: " + compte.getDateCreation());
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to fetch account details", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Account ID", text);
        clipboard.setPrimaryClip(clip);
    }
}
