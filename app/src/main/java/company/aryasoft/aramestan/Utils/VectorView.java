package company.aryasoft.aramestan.Utils;

import android.view.View;

public class VectorView
{
    public int draws;
    public View view ;
    public VectorDrawablePreLollipopHelper.MyDirType dirs;
    public VectorView(int drawable, View v, VectorDrawablePreLollipopHelper.MyDirType dir)
    {
        this.draws = drawable;
        this.view = v;
        this.dirs = dir;
    }

}
