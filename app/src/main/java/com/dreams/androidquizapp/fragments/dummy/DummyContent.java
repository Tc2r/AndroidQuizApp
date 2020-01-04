package com.dreams.androidquizapp.fragments.dummy;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent
{

  /**
   * An array of sample (dummy) items.
   */
  public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

  /**
   * A map of sample (dummy) items, by ID.
   */
  public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

  private static final int COUNT = 25;

  static
  {
    // Add some sample items.
    for (int i = 1; i <= COUNT; i++)
    {
      addItem(createDummyItem(i));
    }
  }

  private static void addItem(DummyItem item)
  {

    ITEMS.add(item);
    ITEM_MAP.put(item.id, item);
  }

  private static DummyItem createDummyItem(int position)
  {

    Random random = new Random();
    String id = String.valueOf(1000 + random.nextInt(2000));

    Lorem lorem = LoremIpsum.getInstance();
    String title = lorem.getWords(2, 5);


    String dateString =
        new StringBuilder().append(1 + random.nextInt(11)).append("/").append(1 + random.nextInt(29)).append("/")
            .append(1 + random.nextInt(29)).toString();

    String contents = lorem.getParagraphs(1, 1);
    String details = lorem.getParagraphs(1, 1);


    return new DummyItem(position, id, title, dateString, makeContents(contents), details);
  }

  private static String makeContents(String contents)
  {

    StringBuilder builder = new StringBuilder();
    builder.append("Changlog: \n ").append(contents);
    return builder.toString();
  }

  /**
   * A dummy item representing a piece of content.
   */
  public static class DummyItem
  {

    public final int position;
    public final String id;
    public final String title;
    public final String date;
    public final String content;
    public final String details;

    public DummyItem(int position, String id, String title, String date, String content, String details)
    {

      this.position = position;
      this.id = id;
      this.title = title;
      this.date = date;
      this.content = content;
      this.details = details;
    }

    @Override
    public String toString()
    {

      return content;
    }
  }
}
