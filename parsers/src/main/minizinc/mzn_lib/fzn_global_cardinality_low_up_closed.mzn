%-----------------------------------------------------------------------------%
% Requires that the number of occurences of 'cover[i]' in 'x' is between
% 'lbound[i]' and 'ubound[i]'.
% The elements of 'x' must take their values from 'cover'.
%-----------------------------------------------------------------------------%

predicate fzn_global_cardinality_low_up_closed(array[int] of var int: x,
                           array[int] of int: cover,
				           array[int] of int: lbound,
                           array[int] of int: ubound) =
    globalCardinalityLowUpChoco(x,cover,lbound,ubound,true);


%-----------------------------------------------------------------------------%
%-----------------------------------------------------------------------------%
