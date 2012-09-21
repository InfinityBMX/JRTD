package com.jeffreyregalia;

public interface Filter<T> {
	public boolean accept(T item);
}
