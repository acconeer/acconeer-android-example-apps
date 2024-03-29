package com.acconeer.bluetooth.presence.views.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

import com.acconeer.bluetooth.presence.R;
import com.acconeer.bluetooth.presence.fragments.ConfirmPreferenceDialogFragmentCompat;

public class ConfirmPreference extends DialogPreference {
    private String dialogText;

    public ConfirmPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.preferences_ConfirmPreference, 0, 0);

        try {
            dialogText = attributes.getString(R.styleable.preferences_ConfirmPreference_dialogText);
        } finally {
            attributes.recycle();
        }

        setPersistent(false);
    }

    public String getDialogText() {
        return dialogText;
    }

    public PreferenceDialogFragmentCompat createPreferenceDialog(ConfirmPreferenceDialogFragmentCompat.ConfirmResult result) {
        return ConfirmPreferenceDialogFragmentCompat.newInstance(getKey(), result);
    }

    @Override
    public int getDialogLayoutResource() {
        return R.layout.confirm_dialog_layout;
    }
}
