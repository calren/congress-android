package com.sunlightlabs.android.congress.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sunlightlabs.android.congress.MenuMain;
import com.sunlightlabs.android.congress.R;

public class ActionBarUtils {
	
	private static boolean popupMenu = false;
	
	static {
	       try {
	           PopupMenuWrapper.checkAvailable();
	           popupMenu = true;
	       } catch (Throwable t) {
	           popupMenu = false;
	       }
	   }
	
	public interface HasActionMenu {
		public void menuSelected(MenuItem item);
	}

	public static void setTitle(Activity activity, String title) {
		setTitle(activity, title, true);
	}

	public static void setTitle(Activity activity, int title) {
		setTitle(activity, activity.getResources().getString(title));
	}

	public static void setTitle(Activity activity, int title, boolean enableButton) {
		setTitle(activity, activity.getResources().getString(title), enableButton);
	}

	public static void setTitle(Activity activity, String title, boolean enableButton) {
		((TextView) activity.findViewById(R.id.title_text)).setText(title);
		
		if (enableButton)
			setTitleButton(activity);
	}

	public static void setTitleButton(final Activity activity) {
		View button = activity.findViewById(R.id.title_button);
		
		button.findViewById(R.id.title_up_icon).setVisibility(View.VISIBLE);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.startActivity(new Intent(activity, MenuMain.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		button.setFocusable(true);
	}

	public static void setTitleSize(Activity activity, float size) {
		((TextView) activity.findViewById(R.id.title_text)).setTextSize(size);
	}

	public static void setActionButton(Activity activity, int id, int icon, View.OnClickListener listener) {
		ViewGroup button = (ViewGroup) activity.findViewById(id);
		button.setOnClickListener(listener);
		((ImageView) button.findViewById(R.id.icon)).setImageResource(icon);
		button.setVisibility(View.VISIBLE);
	}

	public static void setActionIcon(Activity activity, int id, int icon) {
		ViewGroup button = (ViewGroup) activity.findViewById(id);
		((ImageView) button.findViewById(R.id.icon)).setImageResource(icon);
	}

	public static void setActionMenu(final Activity activity, int menuId) {
		if (!popupMenu)
			return;
		
		View menuView = activity.findViewById(R.id.action_menu);
		
		final PopupMenuWrapper menu = new PopupMenuWrapper(activity, menuView);
		
		menu.inflate(menuId);
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				((HasActionMenu) activity).menuSelected(item);
				return false;
			}
		});
		
		menuView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				menu.show();
			}
		});
		
		menuView.setVisibility(View.VISIBLE);
	}

}