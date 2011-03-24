package org.xmlprocess.LircClient.ui;

import java.util.Iterator;

import org.xmlprocess.LircClient.dto.RemotesDTO;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class RemotesView {

	public View getRemotesView(RemotesDTO remoteRoot, Context context) {

		// Log.w(getClass().getSimpleName(),
		// "RemotesView getRemotesView called remoteRoot col size = "+remoteRoot.getColNum());
		TableLayout tl = new TableLayout(context);
		tl.setShrinkAllColumns(true);
		tl.setStretchAllColumns(true);

		int cNum = remoteRoot.getColNum();
		int currCol = 0;
		TableRow tr1 = null;
		// Add the Parent for a back operation
		if (remoteRoot.getParent() != null) {
			tr1 = new TableRow(context);
			UuidTypeButton ubtnParent = new UuidTypeButton(context,
					remoteRoot.getParent());
			String label = remoteRoot.getParent().getLabel();
			if (label == null || label.length() == 0) {
				label = "Remotes";
			}

			ubtnParent.setText("Back to " + label);
			ubtnParent.setTextColor(Color.BLUE);
			ubtnParent.setBackgroundColor(Color.WHITE);
			tr1.addView(ubtnParent);
			tl.addView(tr1);
		}

		int cSize = remoteRoot.getChildren().size();

		if (cNum > cSize) {
			cNum = cSize;
		}
		int cProc = 0;
		for (final Iterator<RemotesDTO> it1 = remoteRoot.getChildren()
				.iterator(); it1.hasNext();) {
			// Log.w(getClass().getSimpleName(),
			// "remoteRoot.getChildren() ITERATING currCol = "+currCol);
			if (currCol == 0) {
				tr1 = new TableRow(context);
			}
			RemotesDTO rdto = it1.next();
			UuidTypeButton ubtn = new UuidTypeButton(context, rdto);
			// Log.w(getClass().getSimpleName(),
			// "Adding Button  currCol = "+currCol + " cNum = "+cNum);
			tr1.addView(ubtn);
			currCol++;
			cProc++;

			if (currCol == cNum || cProc == cSize) {
				tl.addView(tr1);
				currCol = 0;
			}

		}

		ScrollView sv = new ScrollView(context);
		sv.setVerticalScrollBarEnabled(true);
		sv.setHorizontalScrollBarEnabled(true);

		sv.addView(tl);

		return sv;
	}

}
