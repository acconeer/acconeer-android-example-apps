package com.acconeer.bluetooth.presence.views.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceDialogFragmentCompat;

import com.acconeer.bluetooth.presence.R;
import com.acconeer.bluetooth.presence.fragments.ScanningDialogFragment;
import com.acconeer.bluetooth.presence.viewmodels.DeviceViewModel;

public class ScanPreference extends ConfirmPreference {
    private String deviceName;
    private boolean isConnected = false;

    public ScanPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPersistent(false);

        setSummaryProvider((Preference.SummaryProvider<ScanPreference>) p -> {
            if (p.hasDevice()) {
                return deviceName;
            } else {
                return context.getString(R.string.not_connected);
            }
        });
    }

    @Override
    public String getDialogText() {
        return getContext().getString(R.string.disconnect_question);
    }

    @Override
    public CharSequence getPositiveButtonText() {
        if (isConnected) {
            return super.getPositiveButtonText();
        } else {
            return getContext().getString(R.string.start_scan);
        }
    }

    @Override
    public CharSequence getNegativeButtonText() {
        if (isConnected) {
            return super.getNegativeButtonText();
        } else {
            return getContext().getString(R.string.close);
        }
    }

    public void setConnected(String deviceName) {
        isConnected = deviceName != null;

        this.deviceName = deviceName;

        notifyDependencyChange(shouldDisableDependents());
        notifyChanged();
    }

    @Override
    public CharSequence getTitle() {
        if (!hasDevice()) {
            return getContext().getString(R.string.connected_device);
        } else {
            return getContext().getString(R.string.connected);
        }
    }

    @Override
    public CharSequence getDialogTitle() {
        if (isConnected) {
            return getContext().getString(R.string.disconnect);
        } else {
            return getContext().getString(R.string.find_device);
        }
    }

    @Override
    public boolean shouldDisableDependents() {
        return !hasDevice();
    }

    protected boolean hasDevice() {
        return deviceName != null;
    }

    public PreferenceDialogFragmentCompat createPreferenceDialog() {
        if (isConnected) {
            return super.createPreferenceDialog(dialog ->  {
                ViewModelProviders.of(dialog.getActivity()).get(DeviceViewModel.class).disconnect();
            });
        } else {
            return ScanningDialogFragment.newInstance(getKey());
        }
    }

    @Override
    public int getDialogLayoutResource() {
        if (isConnected) {
            return super.getDialogLayoutResource();
        } else {
            return R.layout.scan_dialog_layout;
        }
    }
}
