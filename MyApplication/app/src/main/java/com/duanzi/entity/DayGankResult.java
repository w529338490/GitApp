package com.duanzi.entity;


import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class DayGankResult
{
    boolean error;
    public List<String> results;  //历史日期集合

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public List<String> getResults()
    {
        return results;
    }

    public void setResults(List<String> results)
    {
        this.results = results;
    }
}
