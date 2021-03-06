package com.sigaritus.swu.travel.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSBase;
import com.avos.sns.SNSCallback;
import com.avos.sns.SNSException;
import com.avos.sns.SNSType;
import com.sigaritus.swu.travel.R;
import com.sigaritus.swu.travel.constants.Constants;
import com.sigaritus.swu.travel.util.ToastUtils;


public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    private UserLoginTask mAuthTask = null;
    private SNSType type = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginFormView = findViewById(R.id.email_login_form);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        ImageView sinasso = (ImageView) findViewById(R.id.login_sina);
        ImageView qqsso = (ImageView) findViewById(R.id.login_qq);

        sinasso.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithSinaSso();
            }
        });
        qqsso.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithQqSso();
            }
        });

//        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void register() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        checkInput(email, password);
        AVUser user = new AVUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        //
        Log.i("user-info", password + email);
        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    ToastUtils.showShort("注册成功");
                } else {

                    ToastUtils.showShort("注册失败" + e.getCode() + e.getMessage());
                }
            }
        });

    }

    private boolean checkInput(String email, String password) {
        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            return true;
        }
    }

    private void login(String email, String password) {
        showProgress(true);
        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);
    }

    void loginWithSinaSso() {
        try {
            type = SNSType.AVOSCloudSNSSinaWeibo;
            SNS.setupPlatform(this, SNSType.AVOSCloudSNSSinaWeibo, Constants.Sina_appid,
                    Constants.Sina_appscretkey,
                    Constants.Sina_callback);

            SNS.loginWithCallback(LoginActivity.this, SNSType.AVOSCloudSNSSinaWeibo,
                    new SNSCallback() {

                        @Override
                        public void done(SNSBase base, SNSException error) {
                            if (error == null) {
                                SNS.loginWithAuthData(base.userInfo(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser user, AVException error) {

                                        jump();
                                    }
                                });
                                SNS.associateWithAuthData(AVUser.getCurrentUser(), base.userInfo(), null);


                            }
                        }
                    });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    void loginWithQqSso() {
        try {
            type = SNSType.AVOSCloudSNSQQ;
            SNS.setupPlatform(this, SNSType.AVOSCloudSNSQQ, Constants.Qq_appid,
                    Constants.Qq_appscretkey,
                    Constants.Qq_callback);

            SNS.loginWithCallback(LoginActivity.this, SNSType.AVOSCloudSNSQQ,
                    new SNSCallback() {

                        @Override
                        public void done(SNSBase base, SNSException error) {
                            if (error == null) {

                                SNS.loginWithAuthData(base.userInfo(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser user, AVException error) {
                                        jump();
                                    }
                                });

                            }
                        }
                    });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }


    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (checkInput(email, password)) {
            login(email, password);
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

                AVUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
                    public void done(AVUser user, AVException e) {
                        if (e == null) {
                            ToastUtils.showShort("登录成功");
                        } else {
                            ToastUtils.showShort("登录失败");
                            cancel(true);
                        }
                    }
                });


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                jump();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    void jump() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SNS.onActivityResult(requestCode, resultCode, data, type);
    }
}

