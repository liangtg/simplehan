package com.liangtg.simplehan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.liangtg.base.BaseFragment;
import com.github.liangtg.base.BaseRecyclerViewHolder;
import com.github.liangtg.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.liangtg.simplehan.widget.HanZiView;
import com.liangtg.simplehan.widget.MethodTime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ProjectName: simplehan
 * @ClassName: CommonCharacterFragment
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 19-1-10 上午10:06
 * @UpdateUser: 更新者
 * @UpdateDate: 19-1-10 上午10:06
 * @UpdateRemark: 更新说明
 */
public class CommonCharacterFragment extends BaseFragment {
    private ViewHolder viewHolder;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_common_character, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        text = getString(R.string.common_character_500);
        viewHolder = new ViewHolder(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewHolder = null;
    }

    private class ViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            recyclerView = get(R.id.recyclerview);
            FlexboxLayoutManager manager = new FlexboxLayoutManager(getContext());
            manager.setJustifyContent(JustifyContent.CENTER);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new CharacterAdapter());
        }
    }

    private class AdapterViewHolder extends BaseRecyclerViewHolder {
        HanZiView hanZiView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            hanZiView = (HanZiView) itemView;
        }
    }

    private class CharacterAdapter extends RecyclerView.Adapter<AdapterViewHolder> {
        String[] array = new String[text.length()];

        public CharacterAdapter() {
            for (int i = 0; i < array.length; i++) {
                array[i] = Character.toString(text.charAt(i));
            }
        }

        @NonNull
        @Override
        public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MethodTime m = MethodTime.obtain().tag("onCreateViewHolder");
            View view = getLayoutInflater().inflate(R.layout.item_hanzi, parent, false);
            m.end();
            return new AdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
            holder.hanZiView.setText(array[position]);
        }

        @Override
        public int getItemCount() {
            return array.length;
        }
    }


}
