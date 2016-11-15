package theticks.s2t;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import theticks.s2t.actions.ChartAction;
import theticks.s2t.actions.DefaultAction;
import theticks.s2t.actions.IAction;
import theticks.s2t.actions.ListAction;
import theticks.s2t.actions.MapAction;
import theticks.s2t.actions.NavigateAction;
import theticks.s2t.parser.LanguageParser;

import static android.R.attr.action;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LanguageParserTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }
    
    @Test
    public void stopwordsFilter() throws Exception {
        List<String> result = new LanguageParser(context).filter("What is copyright");
        assertEquals(result.size(), 2);
    }

    @Test
    public void whatIsCopyright() throws Exception {
        IAction action = new LanguageParser(context).parse("What is copyright");
        assertThat(action, instanceOf(NavigateAction.class));
    }

    @Test
    public void copyrightAction() throws Exception {
        IAction action = new LanguageParser(context).parse("Should music copyright be extended");
        assertThat(action, instanceOf(ListAction.class));
    }

    @Test
    public void copyrightStudied() throws Exception {
        IAction action = new LanguageParser(context).parse("Has copyright been studied in Romania");
        assertThat(action, instanceOf(ListAction.class));
    }

    @Test
    public void copyrightGroupedByCountry() throws Exception {
        IAction action = new LanguageParser(context).parse("Show me copyright studies grouped by country");
        assertThat(action, instanceOf(MapAction.class));
    }

    @Test
    public void copyrightGroupedByAuthor() throws Exception {
        IAction action = new LanguageParser(context).parse("Show me copyright studies grouped by author");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void copyrightGroupedByIndustry() throws Exception {
        IAction action = new LanguageParser(context).parse("Show me copyright studies grouped by industry");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void copyrightGroupedByYear() throws Exception {
        IAction action = new LanguageParser(context).parse("Show me copyright studies grouped by year");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void unknown() throws Exception {
        IAction action = new LanguageParser(context).parse("Gibberish");
        assertThat(action, instanceOf(DefaultAction.class));
    }

}
