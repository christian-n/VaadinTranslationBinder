package de.nelius.translation.ui;

import de.nelius.translation.session.Session;

public interface UiProvider<T>
{

   T value(Session session);

}
