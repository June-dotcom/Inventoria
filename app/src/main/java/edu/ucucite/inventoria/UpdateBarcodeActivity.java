package edu.ucucite.inventoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import edu.ucucite.inventoria.item_database.ItemObj;
import edu.ucucite.inventoria.ui.items.ItemsFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static edu.ucucite.inventoria.item_database.ItemMgmt.update_item;

public class UpdateBarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String TAG = "Scanner:";
    private static int CAMERA_REQUEST_CODE = 100;
    private static ItemObj itemObj;
    private static int item_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Intent intent = getIntent();
        itemObj = (ItemObj) intent.getExtras().getSerializable("item_obj");
        item_position = intent.getExtras().getInt("item_position");
        getSupportActionBar().setTitle("Update barcode for " + itemObj.getItem_name());
        checkPermission(Manifest.permission.CAMERA,
                100);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(UpdateBarcodeActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(UpdateBarcodeActivity.this, new String[]{permission}, requestCode);
        } else {
//permission already grant
            mScannerView = new ZXingScannerView(this);
            setContentView(mScannerView);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(UpdateBarcodeActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
                mScannerView = new ZXingScannerView(this);
                setContentView(mScannerView);
//                mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
//                mScannerView.startCamera();
                // Start camera on resume
            } else {
//                Toast.makeText(MainActivity.this,
//                        "Camera Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
                finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission(Manifest.permission.CAMERA,
                CAMERA_REQUEST_CODE);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        dialog_save_barcode(mScannerView, rawResult.getText());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void dialog_save_barcode(View view, String barcode) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        // set the custom layout
        builder.setTitle("Save new barcode id: " + barcode);
        // add a button
        onPause();
        builder.setNeutralButton("SCAN ANOTHER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // save to db and exit
                // update item db new code
                itemObj.setItem_barcode_str(barcode);
                update_item(itemObj, view.getContext());
                startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // proceed to capturing
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    @Override
//    protected void onDestroy() {
//        .close();
//        super.onDestroy();
//    }


}