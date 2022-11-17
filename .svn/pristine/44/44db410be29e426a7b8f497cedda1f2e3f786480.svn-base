package com.ivant.cms.entity.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectList<T> implements Iterable<T>
{
	private int total;
	private List<T> list;
	
	public ObjectList()
	{
		total = 0;
	}
	
	public ObjectList(ObjectList<T> list)
	{
		setList(list.getList());
		setTotal(list.getTotal());
	}

	public void setList(List<T> jobs)
	{
		this.list = jobs;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List<T> getList()
	{
		if (list == null)
		{
			list = new LinkedList<T>();
		}
		return list;
	}

	public int getTotal()
	{
		return total;
	}

	public int getSize()
	{
		if (list == null)
			return 0;

		return list.size();
	}

	public Iterator<T> iterator()
	{
		return getList().iterator();
	}
	
	public T uniqueResult()
	{
		List<T> list = getList();
		
		if (list.size() > 0)
			return list.get(0);
		
		return null;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(100 + Math.abs(total * 50));
		sb.append("Total: ").append(total);
		sb.append(", List: {");
		sb.append(list.toString());
		sb.append("}");
		
		return sb.toString();
	}
}
