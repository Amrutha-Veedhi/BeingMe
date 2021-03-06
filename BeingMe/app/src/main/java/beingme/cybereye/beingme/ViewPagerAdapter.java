package beingme.cybereye.beingme;

/**
 * Created by ibraincpu6 on 10-07-2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[]; // This will Store the titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int numberOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mtitles[], int mnumberOfTabsumb) {
        super(fm);

        this.titles = mtitles;
        this.numberOfTabs = mnumberOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            
            return PostsFragment.newInstance(PostsFragment.PostType.HAPPY);
        }
        else            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return PostsFragment.newInstance(PostsFragment.PostType.SUCCESS);
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
