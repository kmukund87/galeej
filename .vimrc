set hlsearch
set incsearch
set ignorecase
set number
set laststatus=2
set statusline=%r%m%f\ line:\ %l\ col:\ %c
set mouse=a
set autoindent
set tabstop=2 shiftwidth=2 expandtab
set nowrap
syntax on

" Diff colors
hi DiffText   cterm=none ctermfg=Black ctermbg=Red gui=none guifg=Black guibg=Red
hi DiffChange cterm=none ctermfg=Black ctermbg=LightMagenta gui=none guifg=Black guibg=LightMagenta


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

function! DoPrettyXML()
  " save the filetype so we can restore it later
  let l:origft = &ft
  set ft=
  " delete the xml header if it exists. This will
  " permit us to surround the document with fake tags
  " without creating invalid xml.
  1s/<?xml .*?>//e
  " insert fake tags around the entire document.
  " This will permit us to pretty-format excerpts of
  " XML that may contain multiple top-level elements.
  0put ='<PrettyXML>'
  $put ='</PrettyXML>'
  silent %!xmllint --format -
  " xmllint will insert an <?xml?> header. it's easy enough to delete
  " if you don't want it.
  " delete the fake tags
  2d
  $d
  " restore the 'normal' indentation, which is one extra level
  " too deep due to the extra tags we wrapped around the document.
  silent %<
  " back to home
  1
  " restore the filetype
  exe "set ft=" . l:origft
endfunction
command! PrettyXML call DoPrettyXML()

" Copy and paste with \c and \v. Note Cmd + v always available in insert mode
map <Leader>c "+y
map <Leader>v "+p
map <Leader>J :%!jq .<Enter>

map <Leader>X :%g/\(<configuration-source\|<\/application>\|<\?xml\|<application \|<\/configuration-source\|value="${seas.cloud.\)/d

" Json
command Json :%!jq .

" Change CamelCase to under_score and accept numbers in name.
command -range UnderScore :<line1>,<Line2>s#\C\(\<\u[a-z0-9]\+\|[a-z0-9]\+\)\(\u\)#\l\1_\l\2#g

" Change under_score to CamelCase
command -range CamelCase :<line1>,<Line2>s#\(\%(\<\l\+\)\%(_\)\@=\)\|_\(\l\)#\u\1\2#g

call plug#begin()
Plug 'preservim/NERDTree'
call plug#end()
