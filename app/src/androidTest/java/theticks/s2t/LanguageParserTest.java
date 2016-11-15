package theticks.s2t;

import org.junit.Test;

import theticks.s2t.actions.ChartAction;
import theticks.s2t.actions.IAction;
import theticks.s2t.actions.ListAction;
import theticks.s2t.actions.MapAction;
import theticks.s2t.actions.NavigateAction;
import theticks.s2t.parser.LanguageParser;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class LanguageParserTest {
    @Test
    public void whatIsCopyright() throws Exception {
        IAction action = new LanguageParser().parse("What is copyright");
        assertThat(action, instanceOf(NavigateAction.class));
    }

    @Test
    public void copyrightAction() throws Exception {
        IAction action = new LanguageParser().parse("Should music copyright be extended");
        assertThat(action, instanceOf(ListAction.class));
    }

    @Test
    public void copyrightStudied() throws Exception {
        IAction action = new LanguageParser().parse("Has copyright been studied in Romania");
        assertThat(action, instanceOf(ListAction.class));
    }

    @Test
    public void copyrightGroupedByCountry() throws Exception {
        IAction action = new LanguageParser().parse("Show me copyright studies grouped by country");
        assertThat(action, instanceOf(MapAction.class));
    }

    @Test
    public void copyrightGroupedByAuthor() throws Exception {
        IAction action = new LanguageParser().parse("Show me copyright studies grouped by author");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void copyrightGroupedByIndustry() throws Exception {
        IAction action = new LanguageParser().parse("Show me copyright studies grouped by industry");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void copyrightGroupedByYear() throws Exception {
        IAction action = new LanguageParser().parse("Show me copyright studies grouped by year");
        assertThat(action, instanceOf(ChartAction.class));
    }

    @Test
    public void unknown() throws Exception {
        IAction action = new LanguageParser().parse("Gibberish");
        assertThat(action, instanceOf(NavigateAction.class));
    }

}
