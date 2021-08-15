package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.Ins;
import com.cesar31.audiogenerator.instruction.Instruction;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cesar31
 */
public class StackHandler {

    private Stack<Ins> stack;

    public StackHandler(Stack<Ins> stack) {
        this.stack = stack;
    }

    public void checkStackForInstructionsFather(List<Instruction> RESULT, Ins ins) {
        if (stack.isEmpty()) {
            stack.push(ins);
        } else if (stack.peek().getTab() + 1 == ins.getTab()) {
            // ins hijo
            stack.peek().getInstructions().add((Instruction) ins);
            stack.push(ins);
        } else if (ins.getTab() <= stack.peek().getTab()) {
            while (!stack.isEmpty()) {
                if (stack.peek().getTab().intValue() == ins.getTab().intValue()) {
                    // instrucciones hermanos
                    Ins tmp = stack.pop();
                    if (stack.isEmpty()) {
                        // instrucciones hermanos huerfanos
                        RESULT.add((Instruction) tmp);
                        stack.push(ins);
                    } else {
                        // Instrucciones hermanos con padres
                        stack.peek().getInstructions().add((Instruction) ins);
                        stack.push(ins);
                    }
                    break;
                }
                stack.pop();
            }
        }
    }

    public void checkStackForInstructionsNonFather(List<Instruction> RESULT, Instruction instruction) {
        if (stack.isEmpty()) {
            RESULT.add(instruction);
        } else if (stack.peek().getTab() + 1 == instruction.getTab()) {
            // instruccion hijo
            stack.peek().getInstructions().add(instruction);
        } else if (instruction.getTab() <= stack.peek().getTab()) {
            while (!stack.isEmpty()) {
                if (stack.peek().getTab().intValue() == instruction.getTab().intValue()) {
                    /* instrucciones hermanos */
                    Ins tmp = stack.pop();
                    if (stack.isEmpty()) {
                        RESULT.add((Instruction) tmp);
                        RESULT.add(instruction);
                    } else {
                        stack.peek().getInstructions().add(instruction);
                    }
                    break;
                }
                stack.pop();
            }
        }
    }
}
