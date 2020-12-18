/*
 * Copyright (C) 2018-2020 Confidential Technologies GmbH
 *
 * You can purchase a commercial license at https://hwsecurity.dev.
 * Buying such a license is mandatory as soon as you develop commercial
 * activities involving this program without disclosing the source code
 * of your own applications.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.cotech.hw.ui.internal;

import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import de.cotech.hw.ui.R;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class SuccessView {

    private View view;

    private TextView successText;
    private ImageView successImage;

    public SuccessView(@NonNull ViewGroup view) {
        this.view = view;

        successText = view.findViewById(R.id.successText);
        successImage = view.findViewById(R.id.successImage);
    }

    public void setVisibility(int visibility) {
        view.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            AnimatedVectorDrawableHelper.startAnimation(successImage, R.drawable.hwsecurity_success);
        } else {
            Animatable animatable = (Animatable) successImage.getDrawable();
            animatable.stop();
        }
    }

    public int getVisibility() {
        return view.getVisibility();
    }

    public void setText(String message) {
        successText.setText(message);
    }

    public void setText(@StringRes int resid) {
        successText.setText(resid);
    }

}
