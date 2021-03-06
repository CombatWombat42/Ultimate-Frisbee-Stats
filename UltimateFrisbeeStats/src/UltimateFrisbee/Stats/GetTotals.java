package UltimateFrisbee.Stats;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GetTotals extends Activity {
	private frisbeeOpenHelper frisbeeOpenHelper;
	private SQLiteDatabase frisbeeData;
	Cursor statCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		frisbeeOpenHelper = new frisbeeOpenHelper(this);
		frisbeeData = frisbeeOpenHelper.getWritableDatabase();
//		db.execSQL("CREATE TABLE " + POINT_PLAYER_TN + "(point_player_id TIMESTAMP PRIMARY KEY, point_id INTEGER, player_name TEXT)");
//		db.execSQL("CREATE TABLE " + STATS_TN + "(stat_id TIMESTAMP PRIMARY KEY, player_point_id TIMESTAMP, stat TEXT)");

		//statCursor = frisbeeData.query(UltimateFrisbee.Stats.frisbeeOpenHelper.STATS_TN, new String[] {"stat_id" , "player_point_id", "stat"}, null, null, null, null, null);
		String query = "SELECT " + UltimateFrisbee.Stats.frisbeeOpenHelper.POINT_PLAYER_TN +".player_name, " + UltimateFrisbee.Stats.frisbeeOpenHelper.STATS_TN + ".stat FROM " + UltimateFrisbee.Stats.frisbeeOpenHelper.STATS_TN +" JOIN " + UltimateFrisbee.Stats.frisbeeOpenHelper.POINT_PLAYER_TN + " ON " + UltimateFrisbee.Stats.frisbeeOpenHelper.POINT_PLAYER_TN +".point_player_id="+UltimateFrisbee.Stats.frisbeeOpenHelper.STATS_TN+".player_point_id";
		Log.d(UltimateFrisbeeStatsActivity.DEBUG_TAG, query);
		
		statCursor = frisbeeData.rawQuery(query, null);
		ScrollView totalsScroller = new ScrollView(this);
		TableLayout totalsListing = new TableLayout (this);
		
		while(!statCursor.isLast()){
			//XXX this should be done programaticaly not using the constant "0"
			//the 0 should be rosterCursor.getColumnIndex("roster")
			statCursor.moveToNext();
			TableRow tr = new TableRow(this);
			TextView line = new TextView(this);
			line.setText(statCursor.getString(0)+ ", " + statCursor.getString(1) );
			tr.addView(line);
			totalsListing.addView(tr);
			//rosterSPAdapter.add(rosterCursor.getString(0));
		}
		statCursor.close();
		
		
		
		totalsListing.setLayoutParams( new TableLayout.LayoutParams(2,2) );
		totalsListing.setPadding(1,1,1,1);
		totalsScroller.addView(totalsListing);
		super.setContentView(totalsScroller);
	}
}
