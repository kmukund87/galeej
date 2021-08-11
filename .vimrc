set hlsearch
set incsearch
set ignorecase
set number
set laststatus=2
set statusline=%r%m%f\ line:\ %l\ col:\ %c
set mouse=a
set autoindent
set tabstop=2 shiftwidth=2 expandtab
syntax on

" Copy and paste with \c and \v. Note Cmd + v always available in insert mode
map <Leader>c "+y
map <Leader>v "+p

" Diff colors
hi DiffText   cterm=none ctermfg=Black ctermbg=Red gui=none guifg=Black guibg=Red
hi DiffChange cterm=none ctermfg=Black ctermbg=LightMagenta gui=none guifg=Black guibg=LightMagenta

" Change CamelCase to under_score and accept numbers in name.
command -range UnderScore :<line1>,<Line2>s#\C\(\<\u[a-z0-9]\+\|[a-z0-9]\+\)\(\u\)#\l\1_\l\2#g

" Change under_score to CamelCase
command -range CamelCase :<line1>,<Line2>s#\(\%(\<\l\+\)\%(_\)\@=\)\|_\(\l\)#\u\1\2#g

" Json
command Json :%!jq .

colorscheme pablo

if $TERM_PROGRAM =~ "iTerm"
    let &t_SI = "\<Esc>]50;CursorShape=1\x7" " Vertical bar in insert mode
    let &t_EI = "\<Esc>]50;CursorShape=0\x7" " Block in normal mode
endif

highlight Cursor guifg=white guibg=black
highlight iCursor guifg=white guibg=steelblue
set guicursor=n-v-c:block-Cursor
set guicursor+=i:ver100-iCursor
set guicursor+=n-v-c:blinkon0
set guicursor+=i:blinkwait10
