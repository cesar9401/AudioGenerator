
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package com.cesar31.audiogenerator.parser;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class AudioParser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return AudioParserSym.class;
}

  /** Default constructor. */
  @Deprecated
  public AudioParser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public AudioParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public AudioParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\003\000\002\002\003\000\002\002\004\000\002\002" +
    "\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\005\000\006\062\004\122\005\001\002\000\004\002" +
    "\uffff\001\002\000\004\002\001\001\002\000\004\002\007" +
    "\001\002\000\004\002\000\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\005\000\004\002\005\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$AudioParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$AudioParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$AudioParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



	public AudioParser(AudioLex lex) {
		super(lex);
	}

	public void syntax_error(Symbol cur_token) {
		List<String> expected = new ArrayList<>();
		List<Integer> tokens = expected_token_ids();

		System.out.printf("Se encontro: %s -> (%s), linea %d columna %d, se esperaba -> ", cur_token.value, symbl_name_from_id(cur_token.sym), cur_token.left, cur_token.right);
		for(Integer i : tokens) {
			// expected.add(symbl_name_from_id(i));
			System.out.printf("%s, ", symbl_name_from_id(i));
		}
		System.out.println("");
	}


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$AudioParser$actions {
  private final AudioParser parser;

  /** Constructor */
  CUP$AudioParser$actions(AudioParser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$AudioParser$do_action_part00000000(
    int                        CUP$AudioParser$act_num,
    java_cup.runtime.lr_parser CUP$AudioParser$parser,
    java.util.Stack            CUP$AudioParser$stack,
    int                        CUP$AudioParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$AudioParser$result;

      /* select the action based on the action number */
      switch (CUP$AudioParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // inicio ::= STR 
            {
              Object RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$AudioParser$stack.peek()).value;
		 System.out.println(s.toString()); 
              CUP$AudioParser$result = parser.getSymbolFactory().newSymbol("inicio",0, ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()), RESULT);
            }
          return CUP$AudioParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= inicio EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.elementAt(CUP$AudioParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.elementAt(CUP$AudioParser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$AudioParser$stack.elementAt(CUP$AudioParser$top-1)).value;
		RESULT = start_val;
              CUP$AudioParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$AudioParser$stack.elementAt(CUP$AudioParser$top-1)), ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$AudioParser$parser.done_parsing();
          return CUP$AudioParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // inicio ::= INTEGER 
            {
              Object RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()).right;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$AudioParser$stack.peek()).value;
		 System.out.println(i.toString()); 
              CUP$AudioParser$result = parser.getSymbolFactory().newSymbol("inicio",0, ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$AudioParser$stack.peek()), RESULT);
            }
          return CUP$AudioParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$AudioParser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$AudioParser$do_action(
    int                        CUP$AudioParser$act_num,
    java_cup.runtime.lr_parser CUP$AudioParser$parser,
    java.util.Stack            CUP$AudioParser$stack,
    int                        CUP$AudioParser$top)
    throws java.lang.Exception
    {
              return CUP$AudioParser$do_action_part00000000(
                               CUP$AudioParser$act_num,
                               CUP$AudioParser$parser,
                               CUP$AudioParser$stack,
                               CUP$AudioParser$top);
    }
}

}
