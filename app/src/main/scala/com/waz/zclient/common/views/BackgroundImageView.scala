/**
 * Wire
 * Copyright (C) 2018 Wire Swiss GmbH
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
package com.waz.zclient.common.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.waz.model.UserData.Picture
import com.waz.service.ZMessaging
import com.waz.utils.events.Signal
import com.waz.zclient.ViewHelper
import com.waz.zclient.glide._

class BackgroundImageView(val context: Context, val attrs: AttributeSet, val defStyleAttr: Int) extends ImageView(context, attrs, defStyleAttr) with ViewHelper {
  def this(context: Context, attrs: AttributeSet) = this(context, attrs, 0)
  def this(context: Context) = this(context, null)

  private val zms = inject[Signal[ZMessaging]]

  val pictureId: Signal[Picture] = for {
    z <- zms
    Some(picture) <- z.users.selfUser.map(_.picture)
  } yield picture

  setScaleType(ImageView.ScaleType.CENTER_CROP)

  pictureId.onUi(id => BackgroundRequest(id).into(this))
}
