package de.nelius.i18n;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.ComboBox;

import java.util.Collection;
import java.util.Iterator;

/**
 * Some util methods for Vaadin components.
 *
 * @author Christian Nelius
 */
public class VaadinComponentUtil {

    /**
     * Finds the index of the item in the {@link ComboBox}.
     *
     * @param comboBox The {@link ComboBox} that contains the item
     * @param item     The item in the {@link ComboBox}
     * @return Index of item in {@link ComboBox} or -1 if not found.
     */
    public static <T> int indexOf(ComboBox<T> comboBox, T item) {
        Iterator<T> iterator = ((ListDataProvider<T>) comboBox.getDataProvider()).getItems().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().equals(item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
