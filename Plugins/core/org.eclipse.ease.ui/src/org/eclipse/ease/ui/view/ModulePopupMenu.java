package org.eclipse.ease.ui.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.ease.ui.tools.AbstractPopupItem;
import org.eclipse.ease.ui.tools.AbstractPopupMenu;

public class ModulePopupMenu extends AbstractPopupMenu {

	private final List<AbstractPopupItem> fItems = new ArrayList<AbstractPopupItem>();

	public ModulePopupMenu(String name) {
		super(name);
	}

	@Override
	protected void populate() {
		for (AbstractPopupItem item : fItems)
			addPopup(item);
	}

	public void addEntry(AbstractPopupItem item) {
		fItems.add(item);
	}

	public List<AbstractPopupItem> getEntries() {
		Collections.sort(fItems, new Comparator<AbstractPopupItem>() {

			@Override
			public int compare(AbstractPopupItem o1, AbstractPopupItem o2) {
				if ((o1 instanceof AbstractPopupMenu) && (!(o2 instanceof AbstractPopupMenu)))
					return -1;

				if ((o2 instanceof AbstractPopupMenu) && (!(o1 instanceof AbstractPopupMenu)))
					return 1;

				return o1.getDisplayName().compareTo(o2.getDisplayName());
			}
		});

		return fItems;
	}
}
