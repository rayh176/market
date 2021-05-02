package com.st.sub;

import lombok.NonNull;

public interface Listener<S> {
    boolean onMessage(@NonNull S message);
}
