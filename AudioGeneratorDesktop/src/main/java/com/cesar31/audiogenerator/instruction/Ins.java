package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public interface Ins {

    public void setInstructions(List<Instruction> instructions);

    public List<Instruction> getInstructions();

    public Integer getTab();

    public void setTab(Integer tab);
}
