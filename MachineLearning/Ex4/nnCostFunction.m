function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%

% t1 25x401 t2 10x26
% X 5000x400

k = num_labels;
% transform class number to 0/1 value
Y = eye(k)(y, :); 

% Part 1 - Forward Feed and Cost Function

% Ok first add ones to our X matrix and a1 = X

a1 = [ones(m,1) X];

% run sig of theta * input values and get size 5000x25
a2 = sigmoid(a1 * Theta1');
a2 = [ones(size(a2,1),1), a2];

% run sig for output layer on a3 mat size 5000x10
a3 = sigmoid(a2 * Theta2');

% sum over all examples and classes
J = (1/m) * sum ( sum ( ( -Y .* log(a3) - (1-Y).*log(1-a3) ) ) );

% Regularize cost 

% first remove bias column which is the first column (just select all rows and skip first column)
t1_NoBias = Theta1(:, 2:end);
t2_NoBias = Theta2(:, 2:end);

J += (lambda / (2*m) ) * ( sum(sum( t1_NoBias .^2 )) + sum(sum(t2_NoBias .^2)) );

% Part 2 - Backprop... stuff oh boy

% setup accumulation vars and note we will keep things as column vectors in loop
d1 = 0;
d2 = 0;

for i = 1:m
  % Feedforward pass 
  a1 = [1; X(i,:)']; % add bias column
  a2 = [1; sigmoid(Theta1 * a1)]; % add bias column
  a3 = sigmoid(Theta2 * a2); % output layer
  
  % Back prop
  d_a3 = a3 - Y(i, :)';
  d_a2 = (t2_NoBias' * d_a3) .* sigmoidGradient(Theta1 * a1); 
  
  d1 += d_a2 * a1';
  d2 += d_a3 * a2';
  
endfor

Theta1_grad = (1 / m) * d1;
Theta2_grad = (1 / m) * d2;

% Regularize grad

Theta1_grad(:, 2:end) += (lambda/m) * t1_NoBias;
Theta2_grad(:, 2:end) += (lambda/m) * t2_NoBias;


% -------------------------------------------------------------

% =========================================================================

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];


end
