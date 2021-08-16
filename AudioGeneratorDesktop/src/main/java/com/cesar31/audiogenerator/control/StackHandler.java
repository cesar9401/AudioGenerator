package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.*;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cesar31
 */
public class StackHandler {

    private Stack<Ins> stack;
    private Stack<IfInstruction> ifStack;
    private Stack<DoWhile> doStack;

    public StackHandler() {
        this.stack = new Stack();
        this.ifStack = new Stack<>();
        this.doStack = new Stack<>();
    }

    /**
     * Agregar instrucciones que pueden ser padres de otras instrucciones al ast
     * segun identacion
     *
     * @param RESULT
     * @param ins
     */
    public void checkStackForInstructionsFather(List<Instruction> RESULT, Ins ins) {
        if (stack.isEmpty()) {
            stack.push(ins); // Revisar cuando se ejecuta
        } else if (stack.peek().getTab() + 1 == ins.getTab()) {
            // ins hijo
            if (ins instanceof DoWhile) {
                doStack.push((DoWhile) ins);
            }
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
                        if (ins instanceof DoWhile) {
                            doStack.push((DoWhile) ins);
                            stack.peek().getInstructions().add((Instruction) ins);
                            stack.push(ins);
                        } else if (ins instanceof While) {
                            if (!doStack.isEmpty()) {
                                if (doStack.peek().getTab().intValue() == ins.getTab().intValue()) {
                                    if(tmp != doStack.peek()) {
                                        System.out.println("check this out!!");
                                    }
                                    DoWhile dwh = doStack.pop();
                                    dwh.setCondition(((While) ins).getCondition());
                                } else {
                                    System.out.println("Se debe cerrar dowhile");
                                }
                            } else {
                                stack.peek().getInstructions().add((Instruction) ins);
                                stack.push(ins);
                            }
                        } else {
                            stack.peek().getInstructions().add((Instruction) ins);
                            stack.push(ins);
                        }
                    }
                    break;
                }
                stack.pop();
            }
        }
    }

    /**
     * Agregar instrucciones que no son padre de otras instrucciones a donde
     * corresponden segun identacion
     *
     * @param RESULT
     * @param instruction
     */
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

    public void checkStackForInstructionsIf(List<Instruction> RESULT, If ins) {
        if (stack.isEmpty()) {
            stack.push(ins); // esto nunca se ejecuta xd
        } else if (stack.peek().getTab() + 1 == ins.getTab()) {

            if (ins.getType() != null) // nuevo if
            {
                switch (ins.getType()) {
                    case IF:
                        // ins hijo
                        IfInstruction if_ = new IfInstruction(ins.getTab(), ins.getType()); // If.Type.IF
                        if_.getIf_instructions().add(ins); // agregar If, incluye condition
                        // revisar si el stack tiene ifInstruction con tab mayores
                        while (!ifStack.isEmpty() && ifStack.peek().getTab() >= if_.getTab()) {
                            ifStack.pop();
                        }
                        // agregar a ifStack
                        ifStack.push(if_);
                        stack.peek().getInstructions().add(if_);// cambiar tipo de If -> IfInstruction
                        stack.push(ins);
                        break;
                    case ELSE_IF:
                        System.out.println("No es posible agregar else_if aqui :v");
                        break;
                    case ELSE:
                        System.out.println("No es posible agregar else aqui :v");
                        break;
                    default:
                        break;
                }
            }

        } else if (ins.getTab() <= stack.peek().getTab()) {
            while (!stack.isEmpty()) {
                if (stack.peek().getTab().intValue() == ins.getTab().intValue()) {
                    // instrucciones hermanos
                    Ins tmp = stack.pop();
                    if (stack.isEmpty()) {
                        // stack is never emtpy with if
                    } else {
                        // Instrucciones hermanos con padres
                        System.out.println("stack is never empty with if");
                        switch (ins.getType()) {
                            case IF:
                                IfInstruction if_ = new IfInstruction(ins.getTab(), ins.getType()); // If.Type.IF
                                if_.getIf_instructions().add(ins); // agregar If, incluye condition

                                // revisar si el stack tiene ifInstruction con tab mayores
                                while (!ifStack.isEmpty() && ifStack.peek().getTab() >= if_.getTab()) {
                                    ifStack.pop();
                                }

                                ifStack.push(if_);
                                stack.peek().getInstructions().add(if_); // cambiar de tipo de If -> IfInstructions
                                stack.push(ins);
                                break;
                            case ELSE_IF:
                                if (!ifStack.isEmpty()) {
                                    while (!ifStack.isEmpty() && ifStack.peek().getTab() > ins.getTab()) {
                                        ifStack.pop();
                                    }

                                    if (!stack.isEmpty()) {
                                        IfInstruction tmp2 = ifStack.peek();
                                        if (tmp2.getCurrent() == If.Type.IF || tmp2.getCurrent() == If.Type.ELSE_IF) {
                                            tmp2.getIf_instructions().add(ins);
                                            tmp2.setCurrent(If.Type.ELSE_IF);

                                            // agregar al stack para que obtenga instrucciones
                                            stack.push(ins);
                                        } else {
                                            System.out.println("No es posible agregar else");
                                        }
                                    } else {
                                        System.out.println("Error, sino si, no tiene si antes");
                                    }
                                } else {
                                    System.out.println("Error, sino si, no tiene si antes");
                                }
                                break;
                            case ELSE:
                                if (!ifStack.isEmpty()) {
                                    while (!ifStack.isEmpty() && ifStack.peek().getTab() > ins.getTab()) {
                                        ifStack.pop();
                                    }

                                    if (!ifStack.isEmpty()) {
                                        IfInstruction tmp2 = ifStack.peek();
                                        if (tmp2.getCurrent() == If.Type.IF || tmp2.getCurrent() == If.Type.ELSE_IF) {
                                            tmp2.getIf_instructions().add(ins);
                                            tmp2.setCurrent(If.Type.ELSE);

                                            // Eliminar por que ya tiene un else
                                            ifStack.pop();

                                            stack.push(ins);
                                        } else {
                                            System.out.println("No es posible agregar else");
                                        }

                                    } else {
                                        System.out.println("Error, sino no tiene si antes");
                                    }

                                } else {
                                    System.out.println("Error, sino no tiene un si antes");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                }
                stack.pop();
            }
        }
    }

    public Stack<Ins> getStack() {
        return stack;
    }

    public Stack<IfInstruction> getIfStack() {
        return ifStack;
    }

    public Stack<DoWhile> getDoStack() {
        return doStack;
    }
}
