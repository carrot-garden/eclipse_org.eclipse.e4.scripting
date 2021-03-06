package org.eclipse.ease.ui.expression.xtext.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.ease.ui.expression.xtext.services.CoreExpressionGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalCoreExpressionParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'['", "']'", "'InstanceOf'", "'And'", "'{'", "','", "'}'", "'Or'", "'Not'", "'ifEmpty'", "'Iterable'", "'operator'", "'With'", "'AdaptTo'", "'Equal'", "'and'", "'or'", "'activeContexts'", "'activeEditor'", "'activeEditorId'", "'activePartId'", "'activeSite'", "'selection'"
    };
    public static final int RULE_ID=5;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int RULE_STRING=4;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalCoreExpressionParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalCoreExpressionParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalCoreExpressionParser.tokenNames; }
    public String getGrammarFileName() { return "../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g"; }



     	private CoreExpressionGrammarAccess grammarAccess;
     	
        public InternalCoreExpressionParser(TokenStream input, CoreExpressionGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Root";	
       	}
       	
       	@Override
       	protected CoreExpressionGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleRoot"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:67:1: entryRuleRoot returns [EObject current=null] : iv_ruleRoot= ruleRoot EOF ;
    public final EObject entryRuleRoot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoot = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:68:2: (iv_ruleRoot= ruleRoot EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:69:2: iv_ruleRoot= ruleRoot EOF
            {
             newCompositeNode(grammarAccess.getRootRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleRoot_in_entryRuleRoot75);
            iv_ruleRoot=ruleRoot();

            state._fsp--;

             current =iv_ruleRoot; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleRoot85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoot"


    // $ANTLR start "ruleRoot"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:76:1: ruleRoot returns [EObject current=null] : (otherlv_0= '[' ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= ']' ) ;
    public final EObject ruleRoot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_expression_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:79:28: ( (otherlv_0= '[' ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= ']' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:80:1: (otherlv_0= '[' ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= ']' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:80:1: (otherlv_0= '[' ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= ']' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:80:3: otherlv_0= '[' ( (lv_expression_1_0= ruleExpression ) ) otherlv_2= ']'
            {
            otherlv_0=(Token)match(input,11,FollowSets000.FOLLOW_11_in_ruleRoot122); 

                	newLeafNode(otherlv_0, grammarAccess.getRootAccess().getLeftSquareBracketKeyword_0());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:84:1: ( (lv_expression_1_0= ruleExpression ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:85:1: (lv_expression_1_0= ruleExpression )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:85:1: (lv_expression_1_0= ruleExpression )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:86:3: lv_expression_1_0= ruleExpression
            {
             
            	        newCompositeNode(grammarAccess.getRootAccess().getExpressionExpressionParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleRoot143);
            lv_expression_1_0=ruleExpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRootRule());
            	        }
                   		set(
                   			current, 
                   			"expression",
                    		lv_expression_1_0, 
                    		"Expression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleRoot155); 

                	newLeafNode(otherlv_2, grammarAccess.getRootAccess().getRightSquareBracketKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoot"


    // $ANTLR start "entryRuleExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:114:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:115:2: (iv_ruleExpression= ruleExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:116:2: iv_ruleExpression= ruleExpression EOF
            {
             newCompositeNode(grammarAccess.getExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleExpression_in_entryRuleExpression191);
            iv_ruleExpression=ruleExpression();

            state._fsp--;

             current =iv_ruleExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleExpression201); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:123:1: ruleExpression returns [EObject current=null] : (this_InstanceExpression_0= ruleInstanceExpression | this_AndExpression_1= ruleAndExpression | this_OrExpresison_2= ruleOrExpresison | this_NotExpression_3= ruleNotExpression | this_IterableExpression_4= ruleIterableExpression | this_WithExpression_5= ruleWithExpression | this_AdaptExpression_6= ruleAdaptExpression | this_EqualExpression_7= ruleEqualExpression ) ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_InstanceExpression_0 = null;

        EObject this_AndExpression_1 = null;

        EObject this_OrExpresison_2 = null;

        EObject this_NotExpression_3 = null;

        EObject this_IterableExpression_4 = null;

        EObject this_WithExpression_5 = null;

        EObject this_AdaptExpression_6 = null;

        EObject this_EqualExpression_7 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:126:28: ( (this_InstanceExpression_0= ruleInstanceExpression | this_AndExpression_1= ruleAndExpression | this_OrExpresison_2= ruleOrExpresison | this_NotExpression_3= ruleNotExpression | this_IterableExpression_4= ruleIterableExpression | this_WithExpression_5= ruleWithExpression | this_AdaptExpression_6= ruleAdaptExpression | this_EqualExpression_7= ruleEqualExpression ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:127:1: (this_InstanceExpression_0= ruleInstanceExpression | this_AndExpression_1= ruleAndExpression | this_OrExpresison_2= ruleOrExpresison | this_NotExpression_3= ruleNotExpression | this_IterableExpression_4= ruleIterableExpression | this_WithExpression_5= ruleWithExpression | this_AdaptExpression_6= ruleAdaptExpression | this_EqualExpression_7= ruleEqualExpression )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:127:1: (this_InstanceExpression_0= ruleInstanceExpression | this_AndExpression_1= ruleAndExpression | this_OrExpresison_2= ruleOrExpresison | this_NotExpression_3= ruleNotExpression | this_IterableExpression_4= ruleIterableExpression | this_WithExpression_5= ruleWithExpression | this_AdaptExpression_6= ruleAdaptExpression | this_EqualExpression_7= ruleEqualExpression )
            int alt1=8;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt1=1;
                }
                break;
            case 14:
                {
                alt1=2;
                }
                break;
            case 18:
                {
                alt1=3;
                }
                break;
            case 19:
                {
                alt1=4;
                }
                break;
            case 20:
            case 21:
                {
                alt1=5;
                }
                break;
            case 23:
                {
                alt1=6;
                }
                break;
            case 24:
                {
                alt1=7;
                }
                break;
            case 25:
                {
                alt1=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:128:5: this_InstanceExpression_0= ruleInstanceExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getInstanceExpressionParserRuleCall_0()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleInstanceExpression_in_ruleExpression248);
                    this_InstanceExpression_0=ruleInstanceExpression();

                    state._fsp--;

                     
                            current = this_InstanceExpression_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:138:5: this_AndExpression_1= ruleAndExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getAndExpressionParserRuleCall_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleAndExpression_in_ruleExpression275);
                    this_AndExpression_1=ruleAndExpression();

                    state._fsp--;

                     
                            current = this_AndExpression_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:148:5: this_OrExpresison_2= ruleOrExpresison
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getOrExpresisonParserRuleCall_2()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleOrExpresison_in_ruleExpression302);
                    this_OrExpresison_2=ruleOrExpresison();

                    state._fsp--;

                     
                            current = this_OrExpresison_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:158:5: this_NotExpression_3= ruleNotExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getNotExpressionParserRuleCall_3()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_ruleExpression329);
                    this_NotExpression_3=ruleNotExpression();

                    state._fsp--;

                     
                            current = this_NotExpression_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:168:5: this_IterableExpression_4= ruleIterableExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getIterableExpressionParserRuleCall_4()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleIterableExpression_in_ruleExpression356);
                    this_IterableExpression_4=ruleIterableExpression();

                    state._fsp--;

                     
                            current = this_IterableExpression_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:178:5: this_WithExpression_5= ruleWithExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getWithExpressionParserRuleCall_5()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleWithExpression_in_ruleExpression383);
                    this_WithExpression_5=ruleWithExpression();

                    state._fsp--;

                     
                            current = this_WithExpression_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:188:5: this_AdaptExpression_6= ruleAdaptExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getAdaptExpressionParserRuleCall_6()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleAdaptExpression_in_ruleExpression410);
                    this_AdaptExpression_6=ruleAdaptExpression();

                    state._fsp--;

                     
                            current = this_AdaptExpression_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:198:5: this_EqualExpression_7= ruleEqualExpression
                    {
                     
                            newCompositeNode(grammarAccess.getExpressionAccess().getEqualExpressionParserRuleCall_7()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleEqualExpression_in_ruleExpression437);
                    this_EqualExpression_7=ruleEqualExpression();

                    state._fsp--;

                     
                            current = this_EqualExpression_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleInstanceExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:216:1: entryRuleInstanceExpression returns [EObject current=null] : iv_ruleInstanceExpression= ruleInstanceExpression EOF ;
    public final EObject entryRuleInstanceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstanceExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:217:2: (iv_ruleInstanceExpression= ruleInstanceExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:218:2: iv_ruleInstanceExpression= ruleInstanceExpression EOF
            {
             newCompositeNode(grammarAccess.getInstanceExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleInstanceExpression_in_entryRuleInstanceExpression474);
            iv_ruleInstanceExpression=ruleInstanceExpression();

            state._fsp--;

             current =iv_ruleInstanceExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleInstanceExpression484); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInstanceExpression"


    // $ANTLR start "ruleInstanceExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:225:1: ruleInstanceExpression returns [EObject current=null] : (otherlv_0= 'InstanceOf' ( (lv_value_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleInstanceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_1_0=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:228:28: ( (otherlv_0= 'InstanceOf' ( (lv_value_1_0= RULE_STRING ) ) ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:229:1: (otherlv_0= 'InstanceOf' ( (lv_value_1_0= RULE_STRING ) ) )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:229:1: (otherlv_0= 'InstanceOf' ( (lv_value_1_0= RULE_STRING ) ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:229:3: otherlv_0= 'InstanceOf' ( (lv_value_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,13,FollowSets000.FOLLOW_13_in_ruleInstanceExpression521); 

                	newLeafNode(otherlv_0, grammarAccess.getInstanceExpressionAccess().getInstanceOfKeyword_0());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:233:1: ( (lv_value_1_0= RULE_STRING ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:234:1: (lv_value_1_0= RULE_STRING )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:234:1: (lv_value_1_0= RULE_STRING )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:235:3: lv_value_1_0= RULE_STRING
            {
            lv_value_1_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleInstanceExpression538); 

            			newLeafNode(lv_value_1_0, grammarAccess.getInstanceExpressionAccess().getValueSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getInstanceExpressionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_1_0, 
                    		"STRING");
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInstanceExpression"


    // $ANTLR start "entryRuleAndExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:259:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:260:2: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:261:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleAndExpression_in_entryRuleAndExpression579);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAndExpression589); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:268:1: ruleAndExpression returns [EObject current=null] : ( () otherlv_1= 'And' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expressions_3_0 = null;

        EObject lv_expressions_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:271:28: ( ( () otherlv_1= 'And' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:272:1: ( () otherlv_1= 'And' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:272:1: ( () otherlv_1= 'And' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:272:2: () otherlv_1= 'And' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}'
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:272:2: ()
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:273:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getAndExpressionAccess().getAndExpressionAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleAndExpression635); 

                	newLeafNode(otherlv_1, grammarAccess.getAndExpressionAccess().getAndKeyword_1());
                
            otherlv_2=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleAndExpression647); 

                	newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:286:1: ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=13 && LA3_0<=14)||(LA3_0>=18 && LA3_0<=21)||(LA3_0>=23 && LA3_0<=25)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:286:2: ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:286:2: ( (lv_expressions_3_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:287:1: (lv_expressions_3_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:287:1: (lv_expressions_3_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:288:3: lv_expressions_3_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getAndExpressionAccess().getExpressionsExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleAndExpression669);
                    lv_expressions_3_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAndExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_3_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:304:2: (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==16) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:304:4: otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleAndExpression682); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getAndExpressionAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:308:1: ( (lv_expressions_5_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:309:1: (lv_expressions_5_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:309:1: (lv_expressions_5_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:310:3: lv_expressions_5_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAndExpressionAccess().getExpressionsExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleAndExpression703);
                    	    lv_expressions_5_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAndExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_5_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleAndExpression719); 

                	newLeafNode(otherlv_6, grammarAccess.getAndExpressionAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleOrExpresison"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:338:1: entryRuleOrExpresison returns [EObject current=null] : iv_ruleOrExpresison= ruleOrExpresison EOF ;
    public final EObject entryRuleOrExpresison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpresison = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:339:2: (iv_ruleOrExpresison= ruleOrExpresison EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:340:2: iv_ruleOrExpresison= ruleOrExpresison EOF
            {
             newCompositeNode(grammarAccess.getOrExpresisonRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleOrExpresison_in_entryRuleOrExpresison755);
            iv_ruleOrExpresison=ruleOrExpresison();

            state._fsp--;

             current =iv_ruleOrExpresison; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleOrExpresison765); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrExpresison"


    // $ANTLR start "ruleOrExpresison"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:347:1: ruleOrExpresison returns [EObject current=null] : ( () otherlv_1= 'Or' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleOrExpresison() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expressions_3_0 = null;

        EObject lv_expressions_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:350:28: ( ( () otherlv_1= 'Or' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:351:1: ( () otherlv_1= 'Or' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:351:1: ( () otherlv_1= 'Or' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:351:2: () otherlv_1= 'Or' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}'
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:351:2: ()
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:352:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getOrExpresisonAccess().getOrExpresisonAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleOrExpresison811); 

                	newLeafNode(otherlv_1, grammarAccess.getOrExpresisonAccess().getOrKeyword_1());
                
            otherlv_2=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleOrExpresison823); 

                	newLeafNode(otherlv_2, grammarAccess.getOrExpresisonAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:365:1: ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=13 && LA5_0<=14)||(LA5_0>=18 && LA5_0<=21)||(LA5_0>=23 && LA5_0<=25)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:365:2: ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:365:2: ( (lv_expressions_3_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:366:1: (lv_expressions_3_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:366:1: (lv_expressions_3_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:367:3: lv_expressions_3_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getOrExpresisonAccess().getExpressionsExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleOrExpresison845);
                    lv_expressions_3_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getOrExpresisonRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_3_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:383:2: (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==16) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:383:4: otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleOrExpresison858); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getOrExpresisonAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:387:1: ( (lv_expressions_5_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:388:1: (lv_expressions_5_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:388:1: (lv_expressions_5_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:389:3: lv_expressions_5_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getOrExpresisonAccess().getExpressionsExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleOrExpresison879);
                    	    lv_expressions_5_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getOrExpresisonRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_5_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleOrExpresison895); 

                	newLeafNode(otherlv_6, grammarAccess.getOrExpresisonAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrExpresison"


    // $ANTLR start "entryRuleNotExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:417:1: entryRuleNotExpression returns [EObject current=null] : iv_ruleNotExpression= ruleNotExpression EOF ;
    public final EObject entryRuleNotExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:418:2: (iv_ruleNotExpression= ruleNotExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:419:2: iv_ruleNotExpression= ruleNotExpression EOF
            {
             newCompositeNode(grammarAccess.getNotExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_entryRuleNotExpression931);
            iv_ruleNotExpression=ruleNotExpression();

            state._fsp--;

             current =iv_ruleNotExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleNotExpression941); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNotExpression"


    // $ANTLR start "ruleNotExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:426:1: ruleNotExpression returns [EObject current=null] : ( () otherlv_1= 'Not' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleNotExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expressions_3_0 = null;

        EObject lv_expressions_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:429:28: ( ( () otherlv_1= 'Not' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:430:1: ( () otherlv_1= 'Not' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:430:1: ( () otherlv_1= 'Not' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:430:2: () otherlv_1= 'Not' otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}'
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:430:2: ()
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:431:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getNotExpressionAccess().getNotExpressionAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleNotExpression987); 

                	newLeafNode(otherlv_1, grammarAccess.getNotExpressionAccess().getNotKeyword_1());
                
            otherlv_2=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleNotExpression999); 

                	newLeafNode(otherlv_2, grammarAccess.getNotExpressionAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:444:1: ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>=13 && LA7_0<=14)||(LA7_0>=18 && LA7_0<=21)||(LA7_0>=23 && LA7_0<=25)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:444:2: ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:444:2: ( (lv_expressions_3_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:445:1: (lv_expressions_3_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:445:1: (lv_expressions_3_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:446:3: lv_expressions_3_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getNotExpressionAccess().getExpressionsExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleNotExpression1021);
                    lv_expressions_3_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getNotExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_3_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:462:2: (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==16) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:462:4: otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleNotExpression1034); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getNotExpressionAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:466:1: ( (lv_expressions_5_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:467:1: (lv_expressions_5_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:467:1: (lv_expressions_5_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:468:3: lv_expressions_5_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getNotExpressionAccess().getExpressionsExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleNotExpression1055);
                    	    lv_expressions_5_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getNotExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_5_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleNotExpression1071); 

                	newLeafNode(otherlv_6, grammarAccess.getNotExpressionAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNotExpression"


    // $ANTLR start "entryRuleIterableExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:496:1: entryRuleIterableExpression returns [EObject current=null] : iv_ruleIterableExpression= ruleIterableExpression EOF ;
    public final EObject entryRuleIterableExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIterableExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:497:2: (iv_ruleIterableExpression= ruleIterableExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:498:2: iv_ruleIterableExpression= ruleIterableExpression EOF
            {
             newCompositeNode(grammarAccess.getIterableExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleIterableExpression_in_entryRuleIterableExpression1107);
            iv_ruleIterableExpression=ruleIterableExpression();

            state._fsp--;

             current =iv_ruleIterableExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleIterableExpression1117); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIterableExpression"


    // $ANTLR start "ruleIterableExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:505:1: ruleIterableExpression returns [EObject current=null] : ( () ( (lv_ifEmpty_1_0= 'ifEmpty' ) )? otherlv_2= 'Iterable' (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )? otherlv_5= '{' ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )? otherlv_9= '}' ) ;
    public final EObject ruleIterableExpression() throws RecognitionException {
        EObject current = null;

        Token lv_ifEmpty_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_operand_4_0 = null;

        EObject lv_expressions_6_0 = null;

        EObject lv_expressions_8_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:508:28: ( ( () ( (lv_ifEmpty_1_0= 'ifEmpty' ) )? otherlv_2= 'Iterable' (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )? otherlv_5= '{' ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )? otherlv_9= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:509:1: ( () ( (lv_ifEmpty_1_0= 'ifEmpty' ) )? otherlv_2= 'Iterable' (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )? otherlv_5= '{' ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )? otherlv_9= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:509:1: ( () ( (lv_ifEmpty_1_0= 'ifEmpty' ) )? otherlv_2= 'Iterable' (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )? otherlv_5= '{' ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )? otherlv_9= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:509:2: () ( (lv_ifEmpty_1_0= 'ifEmpty' ) )? otherlv_2= 'Iterable' (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )? otherlv_5= '{' ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )? otherlv_9= '}'
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:509:2: ()
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:510:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getIterableExpressionAccess().getIterableExpressionAction_0(),
                        current);
                

            }

            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:515:2: ( (lv_ifEmpty_1_0= 'ifEmpty' ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==20) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:516:1: (lv_ifEmpty_1_0= 'ifEmpty' )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:516:1: (lv_ifEmpty_1_0= 'ifEmpty' )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:517:3: lv_ifEmpty_1_0= 'ifEmpty'
                    {
                    lv_ifEmpty_1_0=(Token)match(input,20,FollowSets000.FOLLOW_20_in_ruleIterableExpression1169); 

                            newLeafNode(lv_ifEmpty_1_0, grammarAccess.getIterableExpressionAccess().getIfEmptyIfEmptyKeyword_1_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getIterableExpressionRule());
                    	        }
                           		setWithLastConsumed(current, "ifEmpty", true, "ifEmpty");
                    	    

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,21,FollowSets000.FOLLOW_21_in_ruleIterableExpression1195); 

                	newLeafNode(otherlv_2, grammarAccess.getIterableExpressionAccess().getIterableKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:534:1: (otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:534:3: otherlv_3= 'operator' ( (lv_operand_4_0= ruleOperator ) )
                    {
                    otherlv_3=(Token)match(input,22,FollowSets000.FOLLOW_22_in_ruleIterableExpression1208); 

                        	newLeafNode(otherlv_3, grammarAccess.getIterableExpressionAccess().getOperatorKeyword_3_0());
                        
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:538:1: ( (lv_operand_4_0= ruleOperator ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:539:1: (lv_operand_4_0= ruleOperator )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:539:1: (lv_operand_4_0= ruleOperator )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:540:3: lv_operand_4_0= ruleOperator
                    {
                     
                    	        newCompositeNode(grammarAccess.getIterableExpressionAccess().getOperandOperatorParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleOperator_in_ruleIterableExpression1229);
                    lv_operand_4_0=ruleOperator();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getIterableExpressionRule());
                    	        }
                           		set(
                           			current, 
                           			"operand",
                            		lv_operand_4_0, 
                            		"Operator");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleIterableExpression1243); 

                	newLeafNode(otherlv_5, grammarAccess.getIterableExpressionAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:560:1: ( ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>=13 && LA11_0<=14)||(LA11_0>=18 && LA11_0<=21)||(LA11_0>=23 && LA11_0<=25)) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:560:2: ( (lv_expressions_6_0= ruleExpression ) ) (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:560:2: ( (lv_expressions_6_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:561:1: (lv_expressions_6_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:561:1: (lv_expressions_6_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:562:3: lv_expressions_6_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getIterableExpressionAccess().getExpressionsExpressionParserRuleCall_5_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleIterableExpression1265);
                    lv_expressions_6_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getIterableExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_6_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:578:2: (otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==16) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:578:4: otherlv_7= ',' ( (lv_expressions_8_0= ruleExpression ) )
                    	    {
                    	    otherlv_7=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleIterableExpression1278); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getIterableExpressionAccess().getCommaKeyword_5_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:582:1: ( (lv_expressions_8_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:583:1: (lv_expressions_8_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:583:1: (lv_expressions_8_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:584:3: lv_expressions_8_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getIterableExpressionAccess().getExpressionsExpressionParserRuleCall_5_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleIterableExpression1299);
                    	    lv_expressions_8_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getIterableExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_8_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleIterableExpression1315); 

                	newLeafNode(otherlv_9, grammarAccess.getIterableExpressionAccess().getRightCurlyBracketKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIterableExpression"


    // $ANTLR start "entryRuleWithExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:612:1: entryRuleWithExpression returns [EObject current=null] : iv_ruleWithExpression= ruleWithExpression EOF ;
    public final EObject entryRuleWithExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWithExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:613:2: (iv_ruleWithExpression= ruleWithExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:614:2: iv_ruleWithExpression= ruleWithExpression EOF
            {
             newCompositeNode(grammarAccess.getWithExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleWithExpression_in_entryRuleWithExpression1351);
            iv_ruleWithExpression=ruleWithExpression();

            state._fsp--;

             current =iv_ruleWithExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleWithExpression1361); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWithExpression"


    // $ANTLR start "ruleWithExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:621:1: ruleWithExpression returns [EObject current=null] : (otherlv_0= 'With' ( (lv_variable_1_0= ruleVariable ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleWithExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_variable_1_0 = null;

        EObject lv_expressions_3_0 = null;

        EObject lv_expressions_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:624:28: ( (otherlv_0= 'With' ( (lv_variable_1_0= ruleVariable ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:625:1: (otherlv_0= 'With' ( (lv_variable_1_0= ruleVariable ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:625:1: (otherlv_0= 'With' ( (lv_variable_1_0= ruleVariable ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:625:3: otherlv_0= 'With' ( (lv_variable_1_0= ruleVariable ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,23,FollowSets000.FOLLOW_23_in_ruleWithExpression1398); 

                	newLeafNode(otherlv_0, grammarAccess.getWithExpressionAccess().getWithKeyword_0());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:629:1: ( (lv_variable_1_0= ruleVariable ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:630:1: (lv_variable_1_0= ruleVariable )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:630:1: (lv_variable_1_0= ruleVariable )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:631:3: lv_variable_1_0= ruleVariable
            {
             
            	        newCompositeNode(grammarAccess.getWithExpressionAccess().getVariableVariableParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleVariable_in_ruleWithExpression1419);
            lv_variable_1_0=ruleVariable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWithExpressionRule());
            	        }
                   		set(
                   			current, 
                   			"variable",
                    		lv_variable_1_0, 
                    		"Variable");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleWithExpression1431); 

                	newLeafNode(otherlv_2, grammarAccess.getWithExpressionAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:651:1: ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=13 && LA13_0<=14)||(LA13_0>=18 && LA13_0<=21)||(LA13_0>=23 && LA13_0<=25)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:651:2: ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:651:2: ( (lv_expressions_3_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:652:1: (lv_expressions_3_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:652:1: (lv_expressions_3_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:653:3: lv_expressions_3_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getWithExpressionAccess().getExpressionsExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleWithExpression1453);
                    lv_expressions_3_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getWithExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_3_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:669:2: (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==16) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:669:4: otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleWithExpression1466); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getWithExpressionAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:673:1: ( (lv_expressions_5_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:674:1: (lv_expressions_5_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:674:1: (lv_expressions_5_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:675:3: lv_expressions_5_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getWithExpressionAccess().getExpressionsExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleWithExpression1487);
                    	    lv_expressions_5_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getWithExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_5_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleWithExpression1503); 

                	newLeafNode(otherlv_6, grammarAccess.getWithExpressionAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWithExpression"


    // $ANTLR start "entryRuleAdaptExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:703:1: entryRuleAdaptExpression returns [EObject current=null] : iv_ruleAdaptExpression= ruleAdaptExpression EOF ;
    public final EObject entryRuleAdaptExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdaptExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:704:2: (iv_ruleAdaptExpression= ruleAdaptExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:705:2: iv_ruleAdaptExpression= ruleAdaptExpression EOF
            {
             newCompositeNode(grammarAccess.getAdaptExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleAdaptExpression_in_entryRuleAdaptExpression1539);
            iv_ruleAdaptExpression=ruleAdaptExpression();

            state._fsp--;

             current =iv_ruleAdaptExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAdaptExpression1549); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAdaptExpression"


    // $ANTLR start "ruleAdaptExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:712:1: ruleAdaptExpression returns [EObject current=null] : (otherlv_0= 'AdaptTo' ( (lv_value_1_0= RULE_STRING ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleAdaptExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expressions_3_0 = null;

        EObject lv_expressions_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:715:28: ( (otherlv_0= 'AdaptTo' ( (lv_value_1_0= RULE_STRING ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:716:1: (otherlv_0= 'AdaptTo' ( (lv_value_1_0= RULE_STRING ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:716:1: (otherlv_0= 'AdaptTo' ( (lv_value_1_0= RULE_STRING ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}' )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:716:3: otherlv_0= 'AdaptTo' ( (lv_value_1_0= RULE_STRING ) ) otherlv_2= '{' ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )? otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleAdaptExpression1586); 

                	newLeafNode(otherlv_0, grammarAccess.getAdaptExpressionAccess().getAdaptToKeyword_0());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:720:1: ( (lv_value_1_0= RULE_STRING ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:721:1: (lv_value_1_0= RULE_STRING )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:721:1: (lv_value_1_0= RULE_STRING )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:722:3: lv_value_1_0= RULE_STRING
            {
            lv_value_1_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleAdaptExpression1603); 

            			newLeafNode(lv_value_1_0, grammarAccess.getAdaptExpressionAccess().getValueSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAdaptExpressionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_1_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_2=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleAdaptExpression1620); 

                	newLeafNode(otherlv_2, grammarAccess.getAdaptExpressionAccess().getLeftCurlyBracketKeyword_2());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:742:1: ( ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=13 && LA15_0<=14)||(LA15_0>=18 && LA15_0<=21)||(LA15_0>=23 && LA15_0<=25)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:742:2: ( (lv_expressions_3_0= ruleExpression ) ) (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:742:2: ( (lv_expressions_3_0= ruleExpression ) )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:743:1: (lv_expressions_3_0= ruleExpression )
                    {
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:743:1: (lv_expressions_3_0= ruleExpression )
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:744:3: lv_expressions_3_0= ruleExpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getAdaptExpressionAccess().getExpressionsExpressionParserRuleCall_3_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleAdaptExpression1642);
                    lv_expressions_3_0=ruleExpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAdaptExpressionRule());
                    	        }
                           		add(
                           			current, 
                           			"expressions",
                            		lv_expressions_3_0, 
                            		"Expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:760:2: (otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==16) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:760:4: otherlv_4= ',' ( (lv_expressions_5_0= ruleExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleAdaptExpression1655); 

                    	        	newLeafNode(otherlv_4, grammarAccess.getAdaptExpressionAccess().getCommaKeyword_3_1_0());
                    	        
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:764:1: ( (lv_expressions_5_0= ruleExpression ) )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:765:1: (lv_expressions_5_0= ruleExpression )
                    	    {
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:765:1: (lv_expressions_5_0= ruleExpression )
                    	    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:766:3: lv_expressions_5_0= ruleExpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getAdaptExpressionAccess().getExpressionsExpressionParserRuleCall_3_1_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleAdaptExpression1676);
                    	    lv_expressions_5_0=ruleExpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getAdaptExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"expressions",
                    	            		lv_expressions_5_0, 
                    	            		"Expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleAdaptExpression1692); 

                	newLeafNode(otherlv_6, grammarAccess.getAdaptExpressionAccess().getRightCurlyBracketKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdaptExpression"


    // $ANTLR start "entryRuleEqualExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:794:1: entryRuleEqualExpression returns [EObject current=null] : iv_ruleEqualExpression= ruleEqualExpression EOF ;
    public final EObject entryRuleEqualExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualExpression = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:795:2: (iv_ruleEqualExpression= ruleEqualExpression EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:796:2: iv_ruleEqualExpression= ruleEqualExpression EOF
            {
             newCompositeNode(grammarAccess.getEqualExpressionRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleEqualExpression_in_entryRuleEqualExpression1728);
            iv_ruleEqualExpression=ruleEqualExpression();

            state._fsp--;

             current =iv_ruleEqualExpression; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleEqualExpression1738); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEqualExpression"


    // $ANTLR start "ruleEqualExpression"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:803:1: ruleEqualExpression returns [EObject current=null] : (otherlv_0= 'Equal' ( (lv_value_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleEqualExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_1_0=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:806:28: ( (otherlv_0= 'Equal' ( (lv_value_1_0= RULE_STRING ) ) ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:807:1: (otherlv_0= 'Equal' ( (lv_value_1_0= RULE_STRING ) ) )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:807:1: (otherlv_0= 'Equal' ( (lv_value_1_0= RULE_STRING ) ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:807:3: otherlv_0= 'Equal' ( (lv_value_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,25,FollowSets000.FOLLOW_25_in_ruleEqualExpression1775); 

                	newLeafNode(otherlv_0, grammarAccess.getEqualExpressionAccess().getEqualKeyword_0());
                
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:811:1: ( (lv_value_1_0= RULE_STRING ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:812:1: (lv_value_1_0= RULE_STRING )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:812:1: (lv_value_1_0= RULE_STRING )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:813:3: lv_value_1_0= RULE_STRING
            {
            lv_value_1_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleEqualExpression1792); 

            			newLeafNode(lv_value_1_0, grammarAccess.getEqualExpressionAccess().getValueSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getEqualExpressionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_1_0, 
                    		"STRING");
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqualExpression"


    // $ANTLR start "entryRuleOperator"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:837:1: entryRuleOperator returns [String current=null] : iv_ruleOperator= ruleOperator EOF ;
    public final String entryRuleOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOperator = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:838:2: (iv_ruleOperator= ruleOperator EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:839:2: iv_ruleOperator= ruleOperator EOF
            {
             newCompositeNode(grammarAccess.getOperatorRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleOperator_in_entryRuleOperator1834);
            iv_ruleOperator=ruleOperator();

            state._fsp--;

             current =iv_ruleOperator.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleOperator1845); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperator"


    // $ANTLR start "ruleOperator"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:846:1: ruleOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'and' | kw= 'or' ) ;
    public final AntlrDatatypeRuleToken ruleOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:849:28: ( (kw= 'and' | kw= 'or' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:850:1: (kw= 'and' | kw= 'or' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:850:1: (kw= 'and' | kw= 'or' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==26) ) {
                alt16=1;
            }
            else if ( (LA16_0==27) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:851:2: kw= 'and'
                    {
                    kw=(Token)match(input,26,FollowSets000.FOLLOW_26_in_ruleOperator1883); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getOperatorAccess().getAndKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:858:2: kw= 'or'
                    {
                    kw=(Token)match(input,27,FollowSets000.FOLLOW_27_in_ruleOperator1902); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getOperatorAccess().getOrKeyword_1()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperator"


    // $ANTLR start "entryRuleVariable"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:873:1: entryRuleVariable returns [String current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final String entryRuleVariable() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVariable = null;


        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:874:2: (iv_ruleVariable= ruleVariable EOF )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:875:2: iv_ruleVariable= ruleVariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleVariable_in_entryRuleVariable1945);
            iv_ruleVariable=ruleVariable();

            state._fsp--;

             current =iv_ruleVariable.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleVariable1956); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:882:1: ruleVariable returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'activeContexts' | kw= 'activeEditor' | kw= 'activeEditorId' | kw= 'activePartId' | kw= 'activeSite' | kw= 'selection' ) ;
    public final AntlrDatatypeRuleToken ruleVariable() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:885:28: ( (kw= 'activeContexts' | kw= 'activeEditor' | kw= 'activeEditorId' | kw= 'activePartId' | kw= 'activeSite' | kw= 'selection' ) )
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:886:1: (kw= 'activeContexts' | kw= 'activeEditor' | kw= 'activeEditorId' | kw= 'activePartId' | kw= 'activeSite' | kw= 'selection' )
            {
            // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:886:1: (kw= 'activeContexts' | kw= 'activeEditor' | kw= 'activeEditorId' | kw= 'activePartId' | kw= 'activeSite' | kw= 'selection' )
            int alt17=6;
            switch ( input.LA(1) ) {
            case 28:
                {
                alt17=1;
                }
                break;
            case 29:
                {
                alt17=2;
                }
                break;
            case 30:
                {
                alt17=3;
                }
                break;
            case 31:
                {
                alt17=4;
                }
                break;
            case 32:
                {
                alt17=5;
                }
                break;
            case 33:
                {
                alt17=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:887:2: kw= 'activeContexts'
                    {
                    kw=(Token)match(input,28,FollowSets000.FOLLOW_28_in_ruleVariable1994); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getActiveContextsKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:894:2: kw= 'activeEditor'
                    {
                    kw=(Token)match(input,29,FollowSets000.FOLLOW_29_in_ruleVariable2013); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getActiveEditorKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:901:2: kw= 'activeEditorId'
                    {
                    kw=(Token)match(input,30,FollowSets000.FOLLOW_30_in_ruleVariable2032); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getActiveEditorIdKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:908:2: kw= 'activePartId'
                    {
                    kw=(Token)match(input,31,FollowSets000.FOLLOW_31_in_ruleVariable2051); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getActivePartIdKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:915:2: kw= 'activeSite'
                    {
                    kw=(Token)match(input,32,FollowSets000.FOLLOW_32_in_ruleVariable2070); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getActiveSiteKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../org.eclipse.ease.ui.expression.xtext/src-gen/org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.g:922:2: kw= 'selection'
                    {
                    kw=(Token)match(input,33,FollowSets000.FOLLOW_33_in_ruleVariable2089); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getSelectionKeyword_5()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable"

    // Delegated rules


 

    
    private static class FollowSets000 {
        public static final BitSet FOLLOW_ruleRoot_in_entryRuleRoot75 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleRoot85 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_11_in_ruleRoot122 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleRoot143 = new BitSet(new long[]{0x0000000000001000L});
        public static final BitSet FOLLOW_12_in_ruleRoot155 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleExpression_in_entryRuleExpression191 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleExpression201 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleInstanceExpression_in_ruleExpression248 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAndExpression_in_ruleExpression275 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOrExpresison_in_ruleExpression302 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotExpression_in_ruleExpression329 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleIterableExpression_in_ruleExpression356 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWithExpression_in_ruleExpression383 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAdaptExpression_in_ruleExpression410 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEqualExpression_in_ruleExpression437 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleInstanceExpression_in_entryRuleInstanceExpression474 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleInstanceExpression484 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_13_in_ruleInstanceExpression521 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleInstanceExpression538 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression579 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression589 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_14_in_ruleAndExpression635 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleAndExpression647 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleAndExpression669 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleAndExpression682 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleAndExpression703 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleAndExpression719 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOrExpresison_in_entryRuleOrExpresison755 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleOrExpresison765 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleOrExpresison811 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleOrExpresison823 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleOrExpresison845 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleOrExpresison858 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleOrExpresison879 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleOrExpresison895 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotExpression_in_entryRuleNotExpression931 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleNotExpression941 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_19_in_ruleNotExpression987 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleNotExpression999 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleNotExpression1021 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleNotExpression1034 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleNotExpression1055 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleNotExpression1071 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleIterableExpression_in_entryRuleIterableExpression1107 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleIterableExpression1117 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_20_in_ruleIterableExpression1169 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_21_in_ruleIterableExpression1195 = new BitSet(new long[]{0x0000000000408000L});
        public static final BitSet FOLLOW_22_in_ruleIterableExpression1208 = new BitSet(new long[]{0x000000000C000000L});
        public static final BitSet FOLLOW_ruleOperator_in_ruleIterableExpression1229 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleIterableExpression1243 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleIterableExpression1265 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleIterableExpression1278 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleIterableExpression1299 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleIterableExpression1315 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWithExpression_in_entryRuleWithExpression1351 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleWithExpression1361 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_23_in_ruleWithExpression1398 = new BitSet(new long[]{0x00000003F0000000L});
        public static final BitSet FOLLOW_ruleVariable_in_ruleWithExpression1419 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleWithExpression1431 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleWithExpression1453 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleWithExpression1466 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleWithExpression1487 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleWithExpression1503 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAdaptExpression_in_entryRuleAdaptExpression1539 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAdaptExpression1549 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_24_in_ruleAdaptExpression1586 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleAdaptExpression1603 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_15_in_ruleAdaptExpression1620 = new BitSet(new long[]{0x0000000003BE6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleAdaptExpression1642 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_16_in_ruleAdaptExpression1655 = new BitSet(new long[]{0x0000000003BC6000L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleAdaptExpression1676 = new BitSet(new long[]{0x0000000000030000L});
        public static final BitSet FOLLOW_17_in_ruleAdaptExpression1692 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEqualExpression_in_entryRuleEqualExpression1728 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleEqualExpression1738 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_25_in_ruleEqualExpression1775 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleEqualExpression1792 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOperator_in_entryRuleOperator1834 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleOperator1845 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_26_in_ruleOperator1883 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_27_in_ruleOperator1902 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleVariable_in_entryRuleVariable1945 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleVariable1956 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_28_in_ruleVariable1994 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_29_in_ruleVariable2013 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_30_in_ruleVariable2032 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_31_in_ruleVariable2051 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_32_in_ruleVariable2070 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_33_in_ruleVariable2089 = new BitSet(new long[]{0x0000000000000002L});
    }


}