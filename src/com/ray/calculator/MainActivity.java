package com.ray.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	// 用到的所有按钮
	private Button btn_0 = null;
	private Button btn_1 = null;
	private Button btn_2 = null;
	private Button btn_3 = null;
	private Button btn_4 = null;
	private Button btn_5 = null;
	private Button btn_6 = null;
	private Button btn_7 = null;
	private Button btn_8 = null;
	private Button btn_9 = null;
	private Button btn_point = null;
	private Button btn_plus = null;
	private Button btn_min = null;
	private Button btn_mul = null;
	private Button btn_div = null;
	private Button btn_clear = null;
	private Button btn_del = null;
	private Button btn_result = null;
	private EditText input_screen = null;
	private boolean result_flag = false;	// 一旦是计算结果，再按其他数字时就把它清空，如果按计算符号，就直接连接
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn_0 = (Button)findViewById(R.id.btn_0);
		btn_1 = (Button)findViewById(R.id.btn_1);
		btn_2 = (Button)findViewById(R.id.btn_2);
		btn_3 = (Button)findViewById(R.id.btn_3);
		btn_4 = (Button)findViewById(R.id.btn_4);
		btn_5 = (Button)findViewById(R.id.btn_5);
		btn_6 = (Button)findViewById(R.id.btn_6);
		btn_7 = (Button)findViewById(R.id.btn_7);
		btn_8 = (Button)findViewById(R.id.btn_8);
		btn_9 = (Button)findViewById(R.id.btn_9);
		btn_point = (Button)findViewById(R.id.btn_point);
		btn_plus = (Button)findViewById(R.id.btn_plus);
		btn_min = (Button)findViewById(R.id.btn_min);
		btn_mul = (Button)findViewById(R.id.btn_mul);
		btn_div = (Button)findViewById(R.id.btn_div);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		btn_del = (Button)findViewById(R.id.btn_del);
		btn_result = (Button)findViewById(R.id.btn_result);
		
		input_screen = (EditText)findViewById(R.id.input_screen);
		
		btn_0.setOnClickListener(this);	// 由于本类还实现了OnClickListener接口，所以直接用this即可
		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_9.setOnClickListener(this);
		btn_point.setOnClickListener(this);
		btn_plus.setOnClickListener(this);
		btn_min.setOnClickListener(this);
		btn_mul.setOnClickListener(this);
		btn_div.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_del.setOnClickListener(this);
		btn_result.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String edtStr = input_screen.getText().toString();
		
		// 判断当前点击的是哪个按钮
		switch (v.getId()) {
		case R.id.btn_0:
		case R.id.btn_1:
		case R.id.btn_2:
		case R.id.btn_3:
		case R.id.btn_4:
		case R.id.btn_5:
		case R.id.btn_6:
		case R.id.btn_7:
		case R.id.btn_8:
		case R.id.btn_9:
		case R.id.btn_point:
			if(result_flag) {
				result_flag = false;
				edtStr = "";
			}
			input_screen.setText(edtStr + ((Button)v).getText().toString());
			break;
		case R.id.btn_plus:
		case R.id.btn_min:
		case R.id.btn_mul:
		case R.id.btn_div:
			if(result_flag)
				result_flag = false;	// 重新按了运算符后，就算重新编辑运算表达式了
			input_screen.setText(edtStr + " " + ((Button)v).getText().toString() + " ");
			break;
		case R.id.btn_clear:
			input_screen.setText("");
			break;
		case R.id.btn_del:
			if(edtStr != null && !edtStr.equals(""))
				input_screen.setText(edtStr.substring(0, edtStr.length()-1));
			break;
		case R.id.btn_result:
			getResult();
			break;
		default:
			break;
		}
	}
	
	private void getResult() {
		if(!result_flag)
			result_flag = true;	// 等号被点击，接着会给出运算结果
		
		// 获取要计算的表达式
		String exp = input_screen.getText().toString();
		
		// 如果是空的，直接返回即可
		if(exp == null || exp.equals(""))
			return;
		// 如果没有空格，意味着没有输入运算符号，就只是一个数字，那么也直接返回
		if(!exp.contains(" "))
			return;
		
		// 以下就是有完整表达式的情况，先截取出两个运算数字和运算符
		String first = exp.substring(0, exp.indexOf(" "));
		String opt = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2);
		String second = exp.substring(exp.indexOf(" ") + 3);
		
		double result = 0;
		if(!first.equals("") && !second.equals("")) {
			if(first.equals("Error") || second.equals("Error")) {
				input_screen.setText("");
				return;
			}
			
			double fd = Double.parseDouble(first);
			double sd = Double.parseDouble(second);
			
			if(opt.equals("+")) {
				result = fd + sd;
			}else if(opt.equals("-")) {
				result = fd - sd;
			}else if(opt.equals("×")) {
				result = fd * sd;		
			}else if(opt.equals("÷")) {
				if(sd == 0) {
					result = 0;
				}else {
					result = fd / sd;
				}
			}
			
			// 如果结果是int类型的， 直接修改为整数形式输出
			if(!first.contains(".") && !second.contains(".")) {
				int r = (int)result;
				input_screen.setText(r + "");
			}else {
				input_screen.setText(result + "");
			}
		}else if(first.equals("") || second.equals("")) {
			input_screen.setText("Error");
		}
	}

}
